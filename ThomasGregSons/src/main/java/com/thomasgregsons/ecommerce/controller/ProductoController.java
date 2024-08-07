package com.thomasgregsons.ecommerce.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thomasgregsons.ecommerce.entity.Producto;

import com.thomasgregsons.ecommerce.service.ProductoService;
import com.thomasgregsons.ecommerce.utils.ApiRoutes;
import com.thomasgregsons.ecommerce.utils.Util;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(ApiRoutes.PRODUCTOS)
public class ProductoController {
	
	@Autowired
	ProductoService productoService;
	@Autowired
	Util utilidades;
	
	@GetMapping
	public ResponseEntity<List<Producto>> productoFindAll(){
		List<Producto>  listaProductos= productoService.findAllProducto();
		return new ResponseEntity<List<Producto>>(listaProductos, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> productoFindbyId(@PathVariable("id") Long id) {
		Producto producto=productoService.findByIdProducto(id);
		return new ResponseEntity<>(producto, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Producto> saveProducto(
			@RequestParam("nombre") @NotBlank String nombre,
			@RequestParam("imagen") @NotNull MultipartFile imagen,
			@RequestParam("numeroReferencia") @NotNull Long numeroReferencia,
			@RequestParam("precio") @NotNull @Positive BigDecimal precio) throws IOException {

		Producto producto = new Producto();
		
		producto.setNombre(nombre);
		producto.setImagen(utilidades.compressZLib(imagen.getBytes()));
		producto.setNumeroReferencia(numeroReferencia);
		producto.setPrecio(precio);
		
		Producto productosave=productoService.saveProducto(producto);
		
		return new ResponseEntity<Producto>(productosave, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Producto> updateProducto(
			@PathVariable("id") Long id,
			@RequestParam("nombre") @NotBlank String nombre,
			@RequestParam("imagen") @NotNull MultipartFile imagen,
			@RequestParam("numeroReferencia") @NotNull  Long numeroReferencia,
			@RequestParam("precio") @NotNull @Positive BigDecimal precio)throws IOException {
		
		Producto producto = new Producto();
		
		producto.setNombre(nombre);
		producto.setImagen(utilidades.compressZLib(imagen.getBytes()));
		producto.setNumeroReferencia(numeroReferencia);
		producto.setPrecio(precio);
		
		Producto productosave=productoService.updateProducto(producto, id);
		
		return new ResponseEntity<Producto>(productosave, HttpStatus.OK);
		
	 }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Producto> deleteProducto(@PathVariable("id") Long id){
		Producto producto=productoService.deleteProducto(id);
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
}
