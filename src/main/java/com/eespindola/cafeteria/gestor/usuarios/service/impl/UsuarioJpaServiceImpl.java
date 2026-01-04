package com.eespindola.cafeteria.gestor.usuarios.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.UsuarioJpa;
import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.mapper.UsuarioJpaMapper;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioJpaService;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioJpaServiceImpl implements UsuarioJpaService {

  private final UsuarioJpa jpa;

  @Autowired
  UsuarioJpaServiceImpl(
          UsuarioJpa usuarioJpa
  ) {
    this.jpa = usuarioJpa;
  }

  @Override
  public Result<Usuario> consultaUsuariosJpa() {
    try {
      List<UsuarioEntity> usuarioEntityList = jpa.findAll();

      List<Usuario> usuarioList = usuarioEntityList.stream()
              .map(UsuarioJpaMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new));

      return ResultBuilder.buildSuccess("Usuarios consultados", usuarioList);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuarios"));
    }
  }

  @Override
  public Result<Usuario> consultarPorFolioJpa(String folio) {
    try {
      Optional<UsuarioEntity> optionalUsuarioEntity = jpa.findByFolio(folio);

      Usuario usuario = optionalUsuarioEntity.stream()
              .map(UsuarioJpaMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new)).getFirst();

      return ResultBuilder.buildSuccess("Usuario consultado", usuario);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuario"));
    }
  }

}
