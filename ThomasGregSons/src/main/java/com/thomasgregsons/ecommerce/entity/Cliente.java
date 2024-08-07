package com.thomasgregsons.ecommerce.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Clientes")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true)
    private Long numeroDocumento;

    @Column(length = 255)
    private String direccion;

    @Column(length = 15)
    private String telefono;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean clienteEliminado;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime fechaEliminación;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean frecuente;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer totalOrdenes;
}
