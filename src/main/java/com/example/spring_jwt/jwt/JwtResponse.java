package com.example.spring_jwt.jwt;

public class JwtResponse {
    private int id;
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String fullName;


    public JwtResponse( String accessToken,int id, String username, String name) {
        this.id = id;
        this.accessToken = accessToken;
        this.username = username;
        this.fullName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
