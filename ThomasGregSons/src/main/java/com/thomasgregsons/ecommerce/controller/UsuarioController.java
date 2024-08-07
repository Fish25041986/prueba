package com.thomasgregsons.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomasgregsons.ecommerce.entity.Usuario;
import com.thomasgregsons.ecommerce.service.UsuarioService;
import com.thomasgregsons.ecommerce.utils.ApiRoutes;

@RestController
@RequestMapping(ApiRoutes.USUARIOS)
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	
	@GetMapping
    public ResponseEntity<List<Usuario>> findAllusuario() {
        List<Usuario> listUsuario = usuarioService.findAllUsuario();
        return new ResponseEntity<>(listUsuario, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findByid(@PathVariable("id") Long id){
    	Usuario usuario=usuarioService.findByidUsuario(id);
    	return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario){
    	Usuario usuarioSave= usuarioService.saveUsuario(usuario);
    	return new ResponseEntity<Usuario>(usuarioSave, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long id){
    	Usuario usuarioUpdate =usuarioService.updateUsuario(usuario, id);
    	return new ResponseEntity<Usuario>(usuarioUpdate, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable("id") Long id){
    	Usuario  usuarioDelete = usuarioService.deleteUsuario(id);
    	return new ResponseEntity<Usuario>(usuarioDelete, HttpStatus.OK);
    }
    

}
