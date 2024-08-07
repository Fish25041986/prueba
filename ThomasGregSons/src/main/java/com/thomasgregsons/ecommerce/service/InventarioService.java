package com.thomasgregsons.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomasgregsons.ecommerce.dto.InventarioDTO;
import com.thomasgregsons.ecommerce.dto.InventarioRespuestaDto;
import com.thomasgregsons.ecommerce.entity.Inventario;
import com.thomasgregsons.ecommerce.exception.MessageError;
import com.thomasgregsons.ecommerce.exception.ResourceNotFoundException;
import com.thomasgregsons.ecommerce.repository.IinventarioRepository;
import com.thomasgregsons.ecommerce.repository.IproductoRepository;
import com.thomasgregsons.ecommerce.utils.Util;


@Service
public class InventarioService {

	@Autowired
	IinventarioRepository iInventarioRepository;
	
	@Autowired
	IproductoRepository iProductoRepository;
	
	/**
	 * Obtiene todos los inventarios disponibles con los valores reales y actuales del inventrio.
	 *
	 * Este método recupera todos los inventarios almacenados en la base de datos
	 * 
	 * Nota: Para este caso cree un DTO para no cargar toda la información del producto en el
	 * inventario object y para mostrar conocimientos
	 * 
	 *
	 * @return Una lista de todos los inventarios
	 * @throws 
	 */
	@Transactional(readOnly = true)
	public List<InventarioRespuestaDto> findAllInventario() {
		return iInventarioRepository.findAllInventario(Util.fechaActualLocalDate());
	}
	
	/**
	 * Busca un inventario por su ID.
	 * 
	 * Nota: Para este caso cree un DTO para no cargar toda la información del producto en el
	 * inventario object y para mostrar conocimientos
	 *
	 * Si el inventario no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param id El ID del inventario a buscar.
	 * @return El inventario encontrado, o lanza una excepción si no se encuentra.
	 * @throws ResourceNotFoundException Si el inventario no se encuentra.
	 */
	 @Transactional(readOnly = true)
	    public InventarioDTO findByIdInventario(Long id) {
	        return iInventarioRepository.findInventarioById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("findByIdInventario", 
	                        MessageError.FIND_BY_ID_FAIL.getMensaje()));
	    }
	
	/**
	 * Guarda un nuevo inventario en la base de datos.
	 *
	 * Antes de guardar, verifica que el producto exista.
	 * Si no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param inventario El inventario a guardar.
	 * @return El inventario guardado.
	 * @throws ResourceNotFoundException si no se encuentr registrado el producto
	 */
	@Transactional(readOnly = false)
	public Inventario saveInventario(Inventario inventario) {

		iProductoRepository.findById(inventario.getProducto().getProductoId())
				.orElseThrow(() -> new ResourceNotFoundException("saveInventario", MessageError.FIND_BY_ID_FAIL.getMensaje()));
		 
	    return iInventarioRepository.save(inventario);
	}
	
	/**
	 * Modifica el inventario de un producto en la base de datos.
	 *
	 * Antes de guardar, verifica que el producto exista.
	 * Si no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param inventario El inventario a guardar.
	 * @return El inventario guardado.
	 * @throws ResourceNotFoundException si no se encuentra registrado el producto
	 */
	@Transactional(readOnly = false)
	public Inventario updateInventario(Inventario inventario, Long idInventario) {
		
		Inventario inventarioSearch= iInventarioRepository.findById(idInventario)
				.orElseThrow(() -> new ResourceNotFoundException("updateInventario", MessageError.FIND_BY_ID_FAIL.getMensaje()));
		
		inventarioSearch.setProducto(inventario.getProducto());
		inventarioSearch.setCantidad(inventario.getCantidad());
		inventarioSearch.setUsuario(inventario.getUsuario());
		inventarioSearch.setTipoMovimiento(inventario.getTipoMovimiento());
		inventarioSearch.setFechaMovimiento(inventario.getFechaMovimiento());
		
		
	    return iInventarioRepository.save(inventarioSearch);
	}
	
	/**
	 * Elimina el inventario de un producto en la base de datos.
	 *
	 * Antes de eliminar, verifica que el producto exista.
	 * Si no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param inventario El inventario a eliminar.
	 * @return void.
	 * @throws ResourceNotFoundException si no se encuentra registrado el inventario
	 */
	@Transactional(readOnly = true)
	public void deleteInventario(Long idInventario) {

		Inventario inventarioSearch= iInventarioRepository.findById(idInventario)
				.orElseThrow(() -> new ResourceNotFoundException("updateInventario", MessageError.FIND_BY_ID_FAIL.getMensaje()));
		
	    iInventarioRepository.deleteById(idInventario);
	}
	
}
