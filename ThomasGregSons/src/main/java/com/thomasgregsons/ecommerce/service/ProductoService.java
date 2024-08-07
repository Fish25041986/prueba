package com.thomasgregsons.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thomasgregsons.ecommerce.entity.Producto;
import com.thomasgregsons.ecommerce.exception.MessageError;
import com.thomasgregsons.ecommerce.exception.ResourceNotFoundException;
import com.thomasgregsons.ecommerce.repository.IproductoRepository;
import com.thomasgregsons.ecommerce.utils.Util;

@Service
public class ProductoService {

	@Autowired
	IproductoRepository iProductoRepository;
	
	@Autowired
	Util utilidades;
	
	
	/**
	 * Obtiene todos los productos y descomprime sus imágenes.
	 *
	 * Este método recupera todos los productos almacenados en la base de datos y, para cada uno de ellos, descomprime la imagen asociada.
	 * La imagen descomprimida reemplaza la imagen original en el objeto Producto.
	 * 
	 * Nota: para este caso  utilizo el map para mostrar conocimientos
	 *
	 * @return Una lista de todos los productos con sus imágenes descomprimidas.
	 * @throws ImageDecompressionException Si ocurre un error al descomprimir alguna imagen.
	 */
	@Transactional(readOnly = true)
	public List<Producto> findAllProducto() {
	    return iProductoRepository.findAll().stream()
	            .map(producto -> {
	                try {
	                    byte[] imagenDescomprimida = utilidades.decompressZLib(producto.getImagen());
	                    producto.setImagen(imagenDescomprimida);
	                    return producto;
	                } catch (Exception e) {
	                	throw new ResourceNotFoundException("findAllProducto", MessageError.DESCOMPRIMIR_IMAGEN_FAIL.getMensaje()+": " + e);
	                }
	            })
	            .collect(Collectors.toList());
	}
	
	
	/**
	 * Busca un producto por su ID y descomprime su imagen.
	 *
	 * Si el producto no se encuentra, lanza una {@link ResourceNotFoundException}.
	 * Si ocurre un error al descomprimir la imagen, lanza una {@link ImageDecompressionException}.
	 * 
	 * Nota: para este caso  utilizo el map para mostrar conocimientos
	 *
	 * @param id El identificador único del producto.
	 * @return El producto encontrado con la imagen descomprimida.
	 * @throws ResourceNotFoundException Si el producto no existe.
	 * @throws ImageDecompressionException Si ocurre un error al descomprimir la imagen.
	 */
	@Transactional(readOnly = true)
	public Producto findByIdProducto(Long id) {
	    return iProductoRepository.findById(id)
	            .map(producto -> {
	                try {
	                    byte[] imageDescomprimida = utilidades.decompressZLib(producto.getImagen());
	                    producto.setImagen(imageDescomprimida);
	                    return producto;
	                } catch (Exception e) {
	                	throw new ResourceNotFoundException("findByIdProducto", MessageError.DESCOMPRIMIR_IMAGEN_FAIL.getMensaje()+": " + e);
	                }
	            })
	            .orElseThrow(() -> new ResourceNotFoundException("findByIdProducto", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	}
	
	
	/**
	 * Guarda un nuevo producto en la base de datos.
	 *
	 * Antes de guardar, verifica si ya existe un proudcto con el mismo número de refreencia.
	 * Si encuentra un duplicado, lanza una excepción {@link ResourceNotFoundException}.
	 *
	 * @param Producto El producto a guardar.
	 * @return El producto guardado.
	 * @throws ResourceNotFoundException Si ya existe un producto con el mismo número de referencia.
	 */
	@Transactional(readOnly = false)
	public Producto saveProducto (Producto producto) {
		Boolean productoSearch=iProductoRepository.findByNumeroReferencia(producto.getNumeroReferencia());
		if (productoSearch) {
	        throw new ResourceNotFoundException("saveProducto", MessageError.INFORMACION_DUPLICADA.getMensaje());
		}
		producto.setProductoActivo(true);
		return iProductoRepository.save(producto);
	}
	
	/**
	 * Actualiza un producto existente.
	 *
	 * Este método busca un producto por su ID y actualiza los campos modificables (todos).
	 *
	 * @param cliente Un objeto Producto que contiene los nuevos valores para actualizar.
	 * @param id El ID del producto a actualizar.
	 * @return El producto actualizado.
	 * @throws ResourceNotFoundException Si no se encuentra un producto con el ID especificado.
	 */
	@Transactional(readOnly = false)
	public Producto updateProducto(Producto producto, Long id) {
		
		Producto productoSearch=iProductoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("updateProducto", MessageError.FIND_BY_ID_FAIL.getMensaje()));
		
		productoSearch.setNombre(producto.getNombre());
		productoSearch.setImagen(producto.getImagen());
		productoSearch.setNumeroReferencia(producto.getNumeroReferencia());
		productoSearch.setPrecio(producto.getPrecio());
		productoSearch.setProductoActivo(producto.getProductoActivo());
		
		return iProductoRepository.save(productoSearch);
	}
	
	/**
	 * Elimina un producto si no tiene información relacionada.
	 *
	 * Este método verifica si el producto especificado tiene registros asociados en las tablas
	 * de inventario y/o detalles de orden. Si no tiene ninguna relación, el producto es eliminado de la base de datos.
	 * 
	 * Nota: para este caso  utilizo el map para mostrar conocimientos
	 * Nota: en este caso devuelvo el objecto Producto , esto solo es para mostrar conocimientos
	 *
	 * @param id El identificador del producto a eliminar.
	 * @return El producto eliminado antes de ser borrado de la base de datos.
	 * @throws ResourceNotFoundException Si el producto tiene información relacionada.
	 */
	@Transactional(readOnly = false)
	public Producto deleteProducto(Long id) {
	    Optional<Producto> optionalProducto = iProductoRepository.findById(id);
	    return optionalProducto.map(producto -> {
	        if (iProductoRepository.tieneInformacionRelacionada(producto.getProductoId()) > 0) {
	            throw new ResourceNotFoundException("deleteProducto", MessageError.DELETE_FAIL_INFORMACION_RELACIONADA.getMensaje());
	        } else {
	            iProductoRepository.delete(producto);
	            return producto;
	        }
	    }).orElseThrow(() -> new ResourceNotFoundException("deleteProducto", MessageError.FIND_BY_ID_FAIL.getMensaje()));
	}
	
}
