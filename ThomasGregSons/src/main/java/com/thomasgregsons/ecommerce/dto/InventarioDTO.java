package com.thomasgregsons.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarioDTO {
    private String nombreProducto;
    private Integer cantidad;
    private Boolean productoActivo;
    
    public InventarioDTO(String nombreProducto, boolean productoActivo, int cantidad) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.productoActivo = productoActivo;
    }

}
