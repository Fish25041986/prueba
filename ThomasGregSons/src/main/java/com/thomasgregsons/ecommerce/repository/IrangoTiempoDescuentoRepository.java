package com.thomasgregsons.ecommerce.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.entity.RangoTiempoDescuento;

@Repository
public interface IrangoTiempoDescuentoRepository extends JpaRepository<RangoTiempoDescuento, Long>{

	 @Query(value = "SELECT MAX(fecha_inicio) FROM Rangos_tiempo_descuento", nativeQuery = true)
	 Date existeRangoFinalizandoHoy();
    
	
	@Query(value = "SELECT COUNT(*) FROM Ordenes WHERE rango_id = :id;", nativeQuery = true)
    Long existenRegistrosRelacionados(@Param("id") Long id);
	
}
