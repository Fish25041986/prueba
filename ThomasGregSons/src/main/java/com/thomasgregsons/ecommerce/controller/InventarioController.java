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

import com.thomasgregsons.ecommerce.dto.InventarioDTO;
import com.thomasgregsons.ecommerce.dto.InventarioRespuestaDto;
import com.thomasgregsons.ecommerce.entity.Inventario;
import com.thomasgregsons.ecommerce.service.InventarioService;
import com.thomasgregsons.ecommerce.utils.ApiRoutes;

@RestController
@RequestMapping(ApiRoutes.INVENTARIO)
public class InventarioController {

	@Autowired
	InventarioService inventarioService;
	
	
    @GetMapping
    public ResponseEntity<List<InventarioRespuestaDto>> findAllInventarios() {
        List<InventarioRespuestaDto> listInventario = inventarioService.findAllInventario();
        return new ResponseEntity<>(listInventario, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> findByidInventario(@PathVariable("id") Long id){
    	InventarioDTO inventario = inventarioService.findByIdInventario(id);
        return ResponseEntity.ok(inventario);
    }
    
    @PostMapping
    public ResponseEntity<Inventario> saveInventario(@RequestBody Inventario inventario){
    	Inventario inventarioSave= inventarioService.saveInventario(inventario);
    	return new ResponseEntity<Inventario>(inventarioSave, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> updateInventario(@RequestBody Inventario inventario, @PathVariable("id") Long id){
    	Inventario inventarioUpdate =inventarioService.updateInventario(inventario, id);
    	return new ResponseEntity<Inventario>(inventarioUpdate, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable("id") Long id){
    	inventarioService.deleteInventario(id);
    	return ResponseEntity.noContent().build();
    }
    
}
