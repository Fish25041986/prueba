package com.thomasgregsons.ecommerce.dto;

import java.math.BigDecimal;

import com.thomasgregsons.ecommerce.entity.Producto;

import lombok.Data;

@Data
public class DetalleOrdenDTO {
	
	private Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;
    
}
