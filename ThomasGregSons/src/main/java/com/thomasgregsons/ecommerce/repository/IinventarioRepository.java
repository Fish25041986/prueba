package com.thomasgregsons.ecommerce.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.dto.InventarioDTO;
import com.thomasgregsons.ecommerce.dto.InventarioRespuestaDto;
import com.thomasgregsons.ecommerce.entity.Inventario;

@Repository
public interface IinventarioRepository extends JpaRepository<Inventario, Long>{
	
	 @Query("SELECT new com.thomasgregsons.ecommerce.dto.InventarioRespuestaDto(p.nombre, p.productoActivo, SUM(CASE WHEN i.tipoMovimiento = 'e' THEN i.cantidad ELSE -i.cantidad END)) " +
	            "FROM Inventario i " +
	            "INNER JOIN i.producto p " +
	            "WHERE i.fechaMovimiento >= :fecha " +
	            "GROUP BY p.nombre, p.productoActivo")
	    List<InventarioRespuestaDto> findAllInventario(@Param("fecha") LocalDate fecha);
	 
	
	@Query("SELECT new com.thomasgregsons.ecommerce.dto.InventarioDTO(p.nombre, p.productoActivo, i.cantidad) " +
		"FROM Inventario i " + 
		"INNER JOIN i.producto p " +
		"WHERE i.inventarioId = :inventarioId")
	Optional<InventarioDTO> findInventarioById(@Param("inventarioId") Long inventarioId);


}
