package com.eespindola.cafeteria.gestor.usuarios.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.UsuarioJpa;
import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.mapper.UsuarioJpaMapper;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioJpaService;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioJpaServiceImpl implements UsuarioJpaService {
  private static final Logger LOG = LoggerFactory.getLogger(UsuarioJpaServiceImpl.class);

  private final UsuarioJpa jpa;

  @Autowired
  UsuarioJpaServiceImpl(
          UsuarioJpa usuarioJpa
  ) {
    this.jpa = usuarioJpa;
  }

  @Override
  public Result<UsuarioRequest> consultaUsuariosJpa() {
    try {
      List<UsuarioEntity> usuarioEntityList = jpa.findAll();

      List<UsuarioRequest> usuarioRequestList = usuarioEntityList.stream()
              .map(UsuarioJpaMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new));

      return ResultBuilder.buildSuccess("Usuarios consultados", usuarioRequestList);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuarios"));
    }
  }

  @Override
  public Result<UsuarioRequest> consultarPorFolioJpa(String folio) {
    try {
      Optional<UsuarioEntity> optionalUsuarioEntity = jpa.findByFolio(folio);

      UsuarioRequest usuarioRequest = optionalUsuarioEntity.stream()
              .map(UsuarioJpaMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new)).getFirst();

      return ResultBuilder.buildSuccess("Usuario consultado", usuarioRequest);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuario"));
    }
  }

}
