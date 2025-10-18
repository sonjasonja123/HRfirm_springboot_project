package com.hr.payload.request;

public class LoginRequest {
    private String username;
    private String password;
    
    // Konstruktor bez argumenata je dobra praksa
    public LoginRequest() {} 
    
    // getters i setters
    public String getUsername(){ return username; }
    public void setUsername(String u){ this.username = u; }
    public String getPassword(){ return password; }
    public void setPassword(String p){ this.password = p; }
}