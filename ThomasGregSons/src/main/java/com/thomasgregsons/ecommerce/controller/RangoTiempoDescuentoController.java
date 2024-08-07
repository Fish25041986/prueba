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

import com.thomasgregsons.ecommerce.entity.RangoTiempoDescuento;
import com.thomasgregsons.ecommerce.service.RangoTiempoDescuentoService;
import com.thomasgregsons.ecommerce.utils.ApiRoutes;

@RestController
@RequestMapping(ApiRoutes.RANGO_TIEMPO)
public class RangoTiempoDescuentoController {

	
	@Autowired
	RangoTiempoDescuentoService rangoTiempoDescuentoService;
	
	
	@GetMapping
    public ResponseEntity<List<RangoTiempoDescuento>> findAllRangoTiempo() {
        List<RangoTiempoDescuento> listRangoTiempoDescuento = rangoTiempoDescuentoService. findAllRangoTiempo();
        return new ResponseEntity<>(listRangoTiempoDescuento, HttpStatus.OK);
    }
	
	//@JsonView(Views.Public.class)
    @GetMapping("/{id}")
    public ResponseEntity<RangoTiempoDescuento> findByIdRangoTiempo(@PathVariable("id") Long id){
    	RangoTiempoDescuento rangoTiempoDescuento=rangoTiempoDescuentoService.findByIdRangoTiempo(id);
    	return new ResponseEntity<>(rangoTiempoDescuento, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<RangoTiempoDescuento> saveRangoTiempo(@RequestBody RangoTiempoDescuento rangoTiempoDescuento){
    	RangoTiempoDescuento RangoTiempoDescuentoSave= rangoTiempoDescuentoService.saveRangoTiempo(rangoTiempoDescuento);
    	return new ResponseEntity<RangoTiempoDescuento>(RangoTiempoDescuentoSave, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RangoTiempoDescuento> updateRangoTiempo(@RequestBody RangoTiempoDescuento rangoTiempoDescuento, @PathVariable("id") Long id){
    	RangoTiempoDescuento RangoTiempoDescuentoUpdate =rangoTiempoDescuentoService.updateRangoTiempo(rangoTiempoDescuento, id);
    	return new ResponseEntity<RangoTiempoDescuento>(RangoTiempoDescuentoUpdate, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRangoTiempo(@PathVariable("id") Long id) {
        rangoTiempoDescuentoService.deleteRangoTiempo(id);
        return ResponseEntity.noContent().build();
    }
    

	
}
