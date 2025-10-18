package com.hr.controller; // Prilagođen HR paketu

import com.hr.security.jwt.JwtTokenProvider; // Provider za token
import com.hr.payload.request.LoginRequest; // DTO za prijavu
import com.hr.payload.response.JwtAuthResponse; // DTO za odgovor

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Metoda za prijavu HR Radnika/Administratora
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        
        // 1. Pokušaj autentifikacije (provera username i password)
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 2. Ako je uspešno, postavi u Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generiši JWT token i vrati ga nazad
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Vraća JWT token frontendu
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }
    
    // Opcionalno, možete dodati @PostMapping("/register") za registraciju novih HR radnika ako je potrebno
}