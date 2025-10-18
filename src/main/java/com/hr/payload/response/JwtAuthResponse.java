package com.hr.payload.response;

public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken(){ return accessToken; }
    public String getTokenType(){ return tokenType; }
    
    // Opcionalno, ali korisno ako ikada pokušate da koristite Lombok ili JSON de-serijalizaciju
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setTokenType(String tokenType) {
        // Obično se ne postavlja, ali je korisno za Jackson
        this.tokenType = tokenType; 
    }
}