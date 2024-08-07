package com.thomasgregsons.ecommerce.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomasgregsons.ecommerce.entity.RangoTiempoDescuento;
import com.thomasgregsons.ecommerce.exception.MessageError;
import com.thomasgregsons.ecommerce.exception.ResourceNotFoundException;
import com.thomasgregsons.ecommerce.repository.IrangoTiempoDescuentoRepository;

@Service
public class RangoTiempoDescuentoService {

	@Autowired
	IrangoTiempoDescuentoRepository iRangoTiempoDescuentoRepository;
	
	/**
	 * Obtiene una lista de todos los rangoDeTiempo de descuento registrados.
	 *
	 * @return Una lista de todos los rangoDeTiempo.
	 */
	@Transactional(readOnly = true)
	public List<RangoTiempoDescuento> findAllRangoTiempo(){
		return iRangoTiempoDescuentoRepository.findAll();
	}
	
	/**
	 * Busca un rangoDeTiempo por su ID.
	 *
	 * Si el rangoDeTiempoo no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param id El ID del rangoDeTiempo a buscar.
	 * @return El rangoDeTiempo, o lanza una excepción si no se encuentra.
	 * @throws ResourceNotFoundException Si el rangoDeTiempo no se encuentra.
	 */
	@Transactional(readOnly = true)
	public RangoTiempoDescuento findByIdRangoTiempo(Long id) {
	    return iRangoTiempoDescuentoRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("findByIdRangoTiempo", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	}
	
	/**
	 * Guarda un nuevo rangoDeTiempo en la base de datos.
	 *
	 * Antes de guardar, verifica que el nuevo rangoDeTiempo sea mayor o igual a la fecha actual.
	 * Si es menor, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param rangoDeTiempo El rangoDeTiempo a guardar.
	 * @return El rangoDeTiempo guardado.
	 */
	@Transactional(readOnly = false)
	public RangoTiempoDescuento saveRangoTiempo(RangoTiempoDescuento rangoDeTiempo) {

		Date rangoTiempoDescuentoSearch = iRangoTiempoDescuentoRepository.existeRangoFinalizandoHoy();
		
		if (rangoTiempoDescuentoSearch != null && rangoTiempoDescuentoSearch.after(rangoDeTiempo.getFechaInicio()) ) {
	        throw new ResourceNotFoundException("saveRangoTiempo", MessageError.RANGO_FECHA_FAIL.getMensaje()+ rangoTiempoDescuentoSearch.toString());
		}
	    
	    rangoDeTiempo.setEstadoActivo(true);
	    
	    return iRangoTiempoDescuentoRepository.save(rangoDeTiempo);
	}
	
	/**
	 * Actualiza un rangoDeTiempo existente.
	 *
	 * Este método busca un rangoDeTiempo por su ID y actualiza los campos modificables(estadoActivo).
	 *No puede modificar los campos fechaInicio y fechafin
	 *
	 * @param rangoDeTiempo Un objeto rangoDeTiempo que contiene los nuevos valores para actualizar.
	 * @param id El ID del rangoDeTiempo a actualizar.
	 * @return El rangoDeTiempo actualizado.
	 * @throws ResourceNotFoundException Si no se encuentra un rangoDeTiempo con el ID especificado.
	 */
	@Transactional(readOnly = false)
	public RangoTiempoDescuento updateRangoTiempo(RangoTiempoDescuento rangoDeTiempo, Long id) {
		
		RangoTiempoDescuento rangoTiempoDescuentoSearch = iRangoTiempoDescuentoRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("updateRangoTiempo", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	    
		rangoTiempoDescuentoSearch.setEstadoActivo(rangoDeTiempo.getEstadoActivo());

	    return iRangoTiempoDescuentoRepository.save(rangoTiempoDescuentoSearch);
	}
	
	/**
	 * Elimina rangoDeTiempo siempre y cuando no exista informacion relacionada.
	 *
	 *Nota: en este caso no vy a devolver nada, esto sole es para mostrar conocimientos
	 *
	 * @param id 
	 * @return El rangoDeTiempo eliminado.
	 * @throws ResourceNotFoundException Si no se encuentra un rangoDeTiempo con el ID especificado o si tiene informacion relacionada.
	 */
	@Transactional(readOnly = false)
	public void deleteRangoTiempo(Long id) {

	    RangoTiempoDescuento rangoTiempoDescuento = iRangoTiempoDescuentoRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("deleteRangoTiempo", MessageError.FIND_BY_ID_FAIL.getMensaje()));

	   if (iRangoTiempoDescuentoRepository.existenRegistrosRelacionados(id)>0) {
	        throw new ResourceNotFoundException("deleteRangoTiempo", MessageError.DELETE_FAIL_INFORMACION_RELACIONADA.getMensaje());
	    }

	    iRangoTiempoDescuentoRepository.deleteById(id);
	}
	
}
