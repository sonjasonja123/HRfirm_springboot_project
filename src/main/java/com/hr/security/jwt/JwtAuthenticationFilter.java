package com.hr.security.jwt;

import com.hr.service.CustomUserDetailsService; // Koristite Vaš servis za učitavanje korisnika
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    // Injektovanje zavisnosti
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Dobavljanje JWT-a iz HTTP zaglavlja zahteva
        String token = getJwtFromRequest(request);

        // 2. Validacija tokena
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            
            // 3. Dobijanje korisničkog imena iz tokena
            String username = tokenProvider.getUsernameFromJWT(token);

            // 4. Učitavanje korisničkih podataka (roles, authorities)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // 5. Kreiranje Spring Security Authentication objekta
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            
            // 6. Postavljanje detalja zahteva
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 7. Postavljanje korisnika u Security Context za trenutni zahtev
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Nastavi lanac filtera (idi na sledeći filter ili na kontroler)
        filterChain.doFilter(request, response);
    }

    // Pomoćna metoda za ekstrakciju JWT tokena iz "Authorization: Bearer <token>" zaglavlja
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}