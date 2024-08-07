package com.thomasgregsons.ecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Productos")
public class Producto implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    @Column(nullable = false)
    private String nombre;
    
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longblob")
	private byte[] imagen;

    @Column(nullable = false)
    private Long numeroReferencia;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;
    
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean productoActivo;

}
