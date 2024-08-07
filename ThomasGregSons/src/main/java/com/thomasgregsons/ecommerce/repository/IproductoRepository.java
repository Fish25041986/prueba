package com.thomasgregsons.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.entity.Producto;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, Long>{
	
	@Query("SELECT EXISTS(SELECT 1 FROM Producto p WHERE p.numeroReferencia = :numeroReferencia)")
	Boolean findByNumeroReferencia(@Param("numeroReferencia") Long numeroReferencia);
	
	@Query(value = "SELECT COUNT(*) FROM Productos p " +
	        "LEFT JOIN Inventarios i ON p.producto_id = i.producto_id " +
	        "LEFT JOIN DetallesOrden d ON p.producto_id = d.producto_id " +
	        "WHERE (i.producto_id IS NOT NULL OR d.producto_id IS NOT NULL) " +
	        "AND p.producto_id = :id", nativeQuery = true)
	Long tieneInformacionRelacionada(@Param("id") Long id);
	
	
	

}
