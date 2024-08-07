package com.thomasgregsons.ecommerce.utils;

public final class ApiRoutes {
	
    // Versión actual de la API
    public static final String API_VERSION = "/api/v1/"; // Cambiar esto a /api/v2, /api/v3, etc. según sea necesario
    
    // Rutas específicas
    public static final String CLIENTES = API_VERSION + "clientes";
    public static final String PRODUCTOS = API_VERSION + "productos";
    public static final String USUARIOS = API_VERSION + "usuarios";
    public static final String INVENTARIOS = API_VERSION + "inventarios";
    public static final String LOGIN = API_VERSION + "login";
    public static final String RANGO_TIEMPO = API_VERSION + "rangotiempo";
    public static final String INVENTARIO = API_VERSION + "inventario";
}
