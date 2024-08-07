package com.thomasgregsons.ecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Ordenes")
/*@JsonIgnoreProperties({"usuario", "hibernateLazyInitializer", "handler"})*/
public class Orden implements Serializable{
	
	private static final long serialVersionUID = -4310027227752446841L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
   /* public String getNombreUsuario() {
        if (usuario != null) {
            return usuario.getNombre();
        }
        return null;
    }*/
    
    /*@JsonView(Views.Public.class)
    public String getNombreUsuario() {
        return usuario != null ? usuario.getNombre() : null;
    }*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private Date fecha;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private String estado;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int descuentoAplicado;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean esAleatorio;
    
    @ManyToOne
    @JoinColumn(name = "rango_id")
    private RangoTiempoDescuento rangoTiempoDescuento;

}
