package com.thomasgregsons.ecommerce.entity;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Rangos_tiempo_descuento")
public class RangoTiempoDescuento implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rangoId;
    
    @Column(nullable = false)
    private String nombreRango;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private int descuento;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean estadoActivo;

}
