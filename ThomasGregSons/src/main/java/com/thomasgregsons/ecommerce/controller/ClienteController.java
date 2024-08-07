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

import com.thomasgregsons.ecommerce.entity.Cliente;
import com.thomasgregsons.ecommerce.service.ClienteService;
import com.thomasgregsons.ecommerce.utils.ApiRoutes;

@RestController
@RequestMapping(ApiRoutes.CLIENTES)
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
    @GetMapping
    public ResponseEntity<List<Cliente>> findAllClientes() {
        List<Cliente> listClientes = clienteService.findAllClientes();
        return new ResponseEntity<>(listClientes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findByid(@PathVariable("id") Long id){
    	Cliente cliente=clienteService.findByIdCliente(id);
    	return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente){
    	Cliente clienteSave= clienteService.saveCliente(cliente);
    	return new ResponseEntity<Cliente>(clienteSave, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente, @PathVariable("id") Long id){
    	Cliente clienteUpdate =clienteService.updateCliente(cliente, id);
    	return new ResponseEntity<Cliente>(clienteUpdate, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("id") Long id){
    	Cliente  clienteDelete = clienteService.deleteCliente(id);
    	return new ResponseEntity<Cliente>(clienteDelete, HttpStatus.OK);
    }
    
}
