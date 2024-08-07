package com.thomasgregsons.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioRespuestaDto {
    private String nombre;
    private boolean productoActivo;
    private Long cantidad;

    public InventarioRespuestaDto(String nombre, boolean productoActivo, Long cantidad) {
        this.nombre = nombre;
        this.productoActivo = productoActivo;
        this.cantidad = cantidad;
    }

}
