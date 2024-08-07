package com.thomasgregsons.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.entity.Orden;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long>{

}
