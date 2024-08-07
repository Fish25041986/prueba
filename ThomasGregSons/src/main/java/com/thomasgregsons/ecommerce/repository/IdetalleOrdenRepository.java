package com.thomasgregsons.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.entity.DetalleOrden;

@Repository
public interface IdetalleOrdenRepository extends JpaRepository<DetalleOrden, Long>{

}
