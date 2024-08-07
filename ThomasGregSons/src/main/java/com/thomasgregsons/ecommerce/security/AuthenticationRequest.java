package com.thomasgregsons.ecommerce.security;


public class AuthenticationRequest {

    private String usuario;
    private String contrasena;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

