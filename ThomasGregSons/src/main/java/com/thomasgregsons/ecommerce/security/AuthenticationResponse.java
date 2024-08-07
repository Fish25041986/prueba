package com.thomasgregsons.ecommerce.security;


public class AuthenticationResponse {
    private String jwt;
    private String user;


    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

