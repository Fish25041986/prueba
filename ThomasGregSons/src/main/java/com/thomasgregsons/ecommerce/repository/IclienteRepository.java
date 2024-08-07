package com.thomasgregsons.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.entity.Cliente;

@Repository
public interface IclienteRepository extends JpaRepository<Cliente, Long>{

	@Query("SELECT c FROM Cliente c WHERE c.numeroDocumento = :numeroDocumento")
	Cliente findByNumeroDocumento(@Param("numeroDocumento") Long numeroDocumento);
	
	
    @Query(value = "SELECT EXISTS (SELECT 1 FROM Ordenes WHERE cliente_id =:id)", nativeQuery = true)
	int tieneInformacionRelacionada(@Param("id") Long id);
	
}
