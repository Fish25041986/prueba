package com.thomasgregsons.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thomasgregsons.ecommerce.entity.Usuario;
import com.thomasgregsons.ecommerce.exception.MessageError;
import com.thomasgregsons.ecommerce.exception.ResourceNotFoundException;
import com.thomasgregsons.ecommerce.repository.IusuarioRepository;
import com.thomasgregsons.ecommerce.security.UsuarioPrincipal;

@Service
public class UsuarioDetailService  implements UserDetailsService {
	
	@Autowired
    private IusuarioRepository iUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        Usuario usuarioSearch = iUsuarioRepository.findByUsuario(username)
        		.orElseThrow(() -> new ResourceNotFoundException("loadUserByUsername", MessageError.ESTADO_INACTIVO_O_NO_EXISTE.getMensaje()));
    		   
        return new UsuarioPrincipal(usuarioSearch);
    }
	
}
