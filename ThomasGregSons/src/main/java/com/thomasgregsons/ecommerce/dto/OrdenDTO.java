package com.thomasgregsons.ecommerce.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrdenDTO {
	
    private Long ordenId;
    private Long usuarioId;
    private Long clienteId;
    private Date fecha;
    private BigDecimal total;
    private String estado;
    private List<DetalleOrdenDTO> detallesOrden;
    
}
