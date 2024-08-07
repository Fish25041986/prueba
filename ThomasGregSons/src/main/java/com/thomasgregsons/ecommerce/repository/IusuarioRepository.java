package com.thomasgregsons.ecommerce.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thomasgregsons.ecommerce.entity.Usuario;

@Repository
public interface IusuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query(value = "SELECT COUNT(*) FROM Usuarios u " +
			            "LEFT JOIN Inventarios i ON u.usuario_id = i.usuario_id " +
			            "LEFT JOIN Ordenes o ON u.usuario_id = o.usuario_id " +
			            "WHERE (i.usuario_id IS NOT NULL OR o.usuario_id IS NOT NULL) " +
			            "AND u.usuario_id = :id " +
			            "GROUP BY u.usuario_id", nativeQuery = true)
	Long tieneInformacionRelacionada(@Param("id") Long id);
	
	
	@Query(nativeQuery = true, value = "SELECT *, (SELECT EXISTS (SELECT 1 FROM Usuarios WHERE usuario = u.usuario AND usuario_activo = true)) AS usuario_existe_activo FROM usuarios u WHERE usuario LIKE :usuario AND usuario_activo = true")
	Optional<Usuario> findByUsuario(@Param("usuario") String usuario);
	
}
