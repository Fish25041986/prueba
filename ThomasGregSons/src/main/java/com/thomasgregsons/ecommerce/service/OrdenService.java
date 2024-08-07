package com.thomasgregsons.ecommerce.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomasgregsons.ecommerce.dto.DetalleOrdenDTO;
import com.thomasgregsons.ecommerce.dto.OrdenDTO;
import com.thomasgregsons.ecommerce.entity.Cliente;
import com.thomasgregsons.ecommerce.entity.DetalleOrden;
import com.thomasgregsons.ecommerce.entity.Orden;
import com.thomasgregsons.ecommerce.entity.RangoTiempoDescuento;
import com.thomasgregsons.ecommerce.entity.Usuario;
import com.thomasgregsons.ecommerce.repository.IOrdenRepository;
import com.thomasgregsons.ecommerce.repository.IclienteRepository;
import com.thomasgregsons.ecommerce.repository.IdetalleOrdenRepository;
import com.thomasgregsons.ecommerce.repository.IrangoTiempoDescuentoRepository;
import com.thomasgregsons.ecommerce.utils.Util;

@Service
public class OrdenService {

	@Autowired
	IOrdenRepository iOrdenRepository;
	
	@Autowired
	IdetalleOrdenRepository idetalleOrdenRepository;
	
	@Autowired
	IrangoTiempoDescuentoRepository irangoTiempoDescuentoRepository;
	
	@Autowired
	IclienteRepository iClienteRepository;

	
	    public Orden crearOrden(Usuario usuario, 
	    		Cliente cliente, 
	    		RangoTiempoDescuento rangoTiempoDescuento,
	    		List<DetalleOrdenDTO> detalles) {
	    	
		        Orden orden = new Orden();
		        orden.setUsuario(usuario);
		        orden.setCliente(cliente);
		        orden.setFecha(new Date(System.currentTimeMillis()));
		        orden.setTotal(calcularTotal(detalles));
		        orden.setEstado("");
		        orden.setDescuentoAplicado(validarDescuentos(rangoTiempoDescuento.getRangoId(), cliente.getClienteId()));
		        orden.setRangoTiempoDescuento(rangoTiempoDescuento);
		        orden.setEsAleatorio(false);
		        
		        iOrdenRepository.save(orden);
		

		        for (DetalleOrdenDTO detalleDTO : detalles) {
		            DetalleOrden detalle = new DetalleOrden();
		            detalle.setOrden(orden);
		            detalle.setProducto(detalleDTO.getProducto());
		            detalle.setCantidad(detalleDTO.getCantidad());
		            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
		            idetalleOrdenRepository.save(detalle);
		        }
		
		  return orden;
	    }   
    


	    private BigDecimal calcularTotal(List<DetalleOrdenDTO> detalles) {
	        BigDecimal total = BigDecimal.ZERO;
	        for (DetalleOrdenDTO detalle : detalles) {
	            total = total.add(detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())));
	        }
	        return total;
	    }
	    
	    /**
	     * Este metodo permite primeramente validar que exista un rango de fecha vigente.
	     * Despues consulta el valor del descuneto para entragarlo a al orden
	     * 
	     * Nota: Casos especiales de funcionamiento 
	     * 
	     * @param id
	     * @return int descuesto
	     */
        private int validarDescuentos(Long idRango, Long idCliente) {
        	
      	   int totalDescuento=0;
        	
	    	Date fechaActualDescuento = irangoTiempoDescuentoRepository.existeRangoFinalizandoHoy();
	    	Optional<Cliente> clienteDescuento = iClienteRepository.findById(idCliente);
	    	
	    	if (fechaActualDescuento !=null) {
		    		Util.fechaActualDate().compareTo(fechaActualDescuento);
		    		if (Util.fechaActualDate().compareTo(fechaActualDescuento)>0) {
							Optional<RangoTiempoDescuento> rangoDeTiempo=irangoTiempoDescuentoRepository.findById(idRango);
							if (rangoDeTiempo.isPresent()) {
								totalDescuento+=rangoDeTiempo.get().getDescuento();
							}
					}
			}
	    	
	    	if (clienteDescuento.isPresent() && clienteDescuento.get().getFrecuente()) {
	    		totalDescuento+=5;
			}
	    	
	    	if (Util.obtenerDescuento()) {
	    		totalDescuento+=50;
			}
	    	
	    	return totalDescuento;
	    }


	
}
