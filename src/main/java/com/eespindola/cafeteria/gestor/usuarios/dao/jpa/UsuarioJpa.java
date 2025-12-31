package com.eespindola.cafeteria.gestor.usuarios.dao.jpa;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpa extends JpaRepository<UsuarioEntity, Object> {

  Optional<UsuarioEntity> findByFolio(String folio);

}
