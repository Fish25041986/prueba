package com.thomasgregsons.ecommerce.entity;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @Column(nullable = false)
    private String nombre; 
    
    @Column(nullable = false)
    private String apellido; 

	private String direccion;

    private String telefono;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String contrasena;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean usuarioActivo;


}
