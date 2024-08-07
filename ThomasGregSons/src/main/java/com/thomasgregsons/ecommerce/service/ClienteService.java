package com.thomasgregsons.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomasgregsons.ecommerce.entity.Cliente;
import com.thomasgregsons.ecommerce.exception.MessageError;
import com.thomasgregsons.ecommerce.exception.ResourceNotFoundException;
import com.thomasgregsons.ecommerce.repository.IclienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	IclienteRepository iClienteRepository;
	
	/**
	 * Obtiene una lista de todos los clientes registrados.
	 *
	 * @return Una lista de todos los clientes.
	 */
	@Transactional(readOnly = true)
	public List<Cliente> findAllClientes(){
		return iClienteRepository.findAll();
	}
	
	/**
	 * Busca un cliente por su ID.
	 *
	 * Si el cliente no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param id El ID del cliente a buscar.
	 * @return El cliente encontrado, o lanza una excepción si no se encuentra.
	 * @throws ResourceNotFoundException Si el cliente no se encuentra.
	 */
	@Transactional(readOnly = true)
	public Cliente findByIdCliente(Long id) {
	    return iClienteRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("findByIdCliente", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	}
	
	/**
	 * Guarda un nuevo cliente en la base de datos.
	 *
	 * Antes de guardar, verifica si ya existe un cliente con el mismo número de documento.
	 * Si encuentra un duplicado, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param cliente El cliente a guardar.
	 * @return El cliente guardado.
	 * @throws ResourceNotFoundException Si ya existe un cliente con el mismo número de documento.
	 */
	@Transactional(readOnly = false)
	public Cliente saveCliente(Cliente cliente) {

	    Cliente clienteSearch = iClienteRepository.findByNumeroDocumento(cliente.getNumeroDocumento());

	    if (clienteSearch != null) {
	        throw new ResourceNotFoundException("saveCliente", MessageError.INFORMACION_DUPLICADA.getMensaje());
	    }
	    cliente.setClienteEliminado(false);
	    cliente.setFechaEliminación(null);
	    cliente.setFrecuente(false);
	    cliente.setTotalOrdenes(0);
	    
	    return iClienteRepository.save(cliente);
	}
	
	/**
	 * Actualiza un cliente existente.
	 *
	 * Este método busca un cliente por su ID y actualiza los campos modificables (nombre, dirección, teléfono, clienteEliminado y email).
	 * Los campos `clienteId`, `frecuente` y `totalOrdenes` no pueden ser modificados.
	 *
	 * @param cliente Un objeto Cliente que contiene los nuevos valores para actualizar.
	 * @param id El ID del cliente a actualizar.
	 * @return El cliente actualizado.
	 * @throws ResourceNotFoundException Si no se encuentra un cliente con el ID especificado.
	 */
	@Transactional(readOnly = false)
	public Cliente updateCliente(Cliente cliente, Long id) {
		
	    Cliente clienteSearch = iClienteRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("updateCliente", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	    
	    clienteSearch.setNombre(cliente.getNombre());
	    clienteSearch.setNumeroDocumento(cliente.getNumeroDocumento());
	    clienteSearch.setDireccion(cliente.getDireccion());
	    clienteSearch.setTelefono(cliente.getTelefono());
	    clienteSearch.setEmail(cliente.getEmail());
	  
	    if (clienteSearch.getClienteEliminado() && !cliente.getClienteEliminado()) {
	    	clienteSearch.setClienteEliminado(cliente.getClienteEliminado());
		}

	    return iClienteRepository.save(clienteSearch);
	}
	
	/**
	 * Elimina actualizando el estado un cliente existente clienteEliminado a true.
	 *
	 * Este método busca un cliente por su ID y actualiza  los campos modificables (clienteEliminado, fechaEliminación y frecuente).
	 * 
	 * Nota: en este caso devuelvo el objecto Cliente , esto solo es para mostrar conocimientos
	 *
	 * @param cliente Un objeto Cliente que contiene los nuevos valores para actualizar.
	 * @param id El ID del cliente a actualizar.
	 * @return El cliente actualizado.
	 * @throws ResourceNotFoundException Si no se encuentra un cliente con el ID especificado.
	 */
	@Transactional(readOnly = true)
	public Cliente deleteCliente(Long id) {
	    Cliente clienteEliminado = iClienteRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("deleteCliente", MessageError.FIND_BY_ID_FAIL.getMensaje()));
 
	    
	    if (iClienteRepository.tieneInformacionRelacionada(id)>0) {
	        throw new ResourceNotFoundException("deleteCliente", MessageError.DELETE_FAIL_INFORMACION_RELACIONADA.getMensaje());
		}
	    
	    clienteEliminado.setClienteEliminado(true);
	    clienteEliminado.setFechaEliminación(LocalDateTime.now());
	    clienteEliminado.setFrecuente(false);
	    return iClienteRepository.save(clienteEliminado);
	}
	
}
