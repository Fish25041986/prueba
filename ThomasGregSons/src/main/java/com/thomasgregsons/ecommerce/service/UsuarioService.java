package com.thomasgregsons.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomasgregsons.ecommerce.entity.Usuario;
import com.thomasgregsons.ecommerce.exception.MessageError;
import com.thomasgregsons.ecommerce.exception.ResourceNotFoundException;
import com.thomasgregsons.ecommerce.repository.IusuarioRepository;
import com.thomasgregsons.ecommerce.utils.Mail;
import com.thomasgregsons.ecommerce.utils.Util;

@Service
public class UsuarioService {

	@Autowired
	IusuarioRepository iUsuarioRepository;
	@Autowired
	Util utilidades;
	@Autowired
    Mail mail;
	
	/**
	 * Obtiene una lista de todos los usuarios registrados.
	 *
	 * @return Una lista de todos los usuarios.
	 */
	@Transactional(readOnly = true)
	public List<Usuario> findAllUsuario() {
		return iUsuarioRepository.findAll();
	}
	
	/**
	 * Busca un usuario por su ID.
	 *
	 * Si el usuario no se encuentra, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param id El ID del usuario a buscar.
	 * @return El usuario encontrado, o lanza una excepción si no se encuentra.
	 * @throws ResourceNotFoundException Si el usuario no se encuentra.
	 */
	@Transactional(readOnly = true)
	public Usuario findByidUsuario(Long id) {
		return iUsuarioRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("findByidUsuario", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	}
	
	/**
	 * Este método guarda un objeto Usuario en la base de datos.
	 * El usuario lo genera de forma automatica, asi mismo la contrasenia y envia un corrreo de confirmacion 
	 * con el usuario y la contraseña
	 * 
	 * @param usuario El objeto Usuario a guardar.
	 * @return El objeto Usuario guardado en la base de datos.
	 * 
	 * @Transactional(readOnly = false)  // Indica que el método realiza una operación de escritura en la base de datos.
	 */
	@Transactional(readOnly = false)
	public Usuario saveUsuario(Usuario usuario) {
		
		String contrasenaRandom = utilidades.generadorContraseña();
		
		usuario.setUsuario(utilidades.generarNombreUsuario(usuario.getNombre(), usuario.getApellido()));
		usuario.setContrasena(utilidades.encryptPassword(contrasenaRandom));
		usuario.setUsuarioActivo(true);
		
		mail.sendSimpleMessage(usuario.getEmail(), "usuario y contraseña", "usuario: "+usuario.getUsuario()+ " contraseña: "+contrasenaRandom);
		
        return iUsuarioRepository.save(usuario);
	}
	
	/**
	 * Actualiza un usuario existente.
	 *
	 * Este método busca un usuario por su ID y actualiza los campos modificables.
	 * Los campos `nombreUsuario`, `contrasena` y `usuarioactivo` no pueden ser modificados.
	 *
	 * @param cliente Un objeto Cliente que contiene los nuevos valores para actualizar.
	 * @param id El ID del cliente a actualizar.
	 * @return El cliente actualizado.
	 * @throws ResourceNotFoundException Si no se encuentra un cliente con el ID especificado.
	 */
	@Transactional(readOnly = false)
	public Usuario updateUsuario(Usuario usuario, Long id) {
		
		Usuario usuarioSearch= iUsuarioRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("findByidUsuario", MessageError.FIND_BY_ID_FAIL.getMensaje()));
		
		usuarioSearch.setNombre(usuario.getNombre());
		usuarioSearch.setApellido(usuario.getApellido());
		usuarioSearch.setDireccion(usuario.getDireccion());
		usuarioSearch.setTelefono(usuario.getTelefono());
		usuarioSearch.setEmail(usuario.getEmail());
		usuarioSearch.setUsuarioActivo(usuario.getUsuarioActivo());
		
        return iUsuarioRepository.save(usuarioSearch);
	}
	
	/**
	 * Elimina un usuario si no tiene información relacionada.
	 *
	 * Este método verifica si el usuario especificado tiene registros asociados en las tablas
	 * de inventario y/o  ordenes. Si no tiene ninguna relación, el usuario es eliminado de la base de datos.
	 *
	 * @param id El identificador del usuario a eliminar.
	 * @return El usuario eliminado antes de ser borrado de la base de datos.
	 * @throws ResourceNotFoundException Si el usuario tiene información relacionada.
	 */
	@Transactional(readOnly = false)
	public Usuario deleteUsuario(Long id) {
		
	    Usuario usuario = iUsuarioRepository.findById(id).orElse(null);

	    if (usuario == null) {
	        throw new ResourceNotFoundException("deleteUsuario", MessageError.FIND_BY_ID_FAIL.getMensaje());
	    }

	    Long count = iUsuarioRepository.tieneInformacionRelacionada(usuario.getUsuarioId());
	    if (count != null && count > 0) {
	        throw new ResourceNotFoundException("deleteUsuario", MessageError.DELETE_FAIL_INFORMACION_RELACIONADA.getMensaje());
	    }

	    iUsuarioRepository.delete(usuario);
	    return usuario;
	}
	
	/**
	 * Esta metodo busca un usuario por su nombre
	 * 
	 * @param usuario
	 * @return usuario
	 */
	@Transactional(readOnly = false)
	 public Usuario findByUsuario(String usuario) {
        return iUsuarioRepository.findByUsuario(usuario).get();
    }
	
}
