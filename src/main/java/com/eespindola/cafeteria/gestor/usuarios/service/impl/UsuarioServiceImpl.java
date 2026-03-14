package com.eespindola.cafeteria.gestor.usuarios.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.mapper.UsuarioMapper;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioService;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
  private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

  private final UsuarioRepository repository;

  @Autowired
  UsuarioServiceImpl(
          UsuarioRepository usuarioRepository
  ) {
    this.repository = usuarioRepository;
  }

  @Override
  public Result<UsuarioResponse> consultarUsuarios() {
    try {
      List<UsuarioDto> usuarioDtoList = repository.getAll();

      List<UsuarioResponse> usuarioResponseList = usuarioDtoList.stream()
              .map(UsuarioMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new));

      return ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, usuarioResponseList);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuarios"));
    }
  }

  @Override
  public Result<UsuarioResponse> consultarPorFolio(String folio) {
    try {
      UsuarioDto usuarioDto = repository.getByFolio(folio);

      UsuarioResponse usuarioResponse = UsuarioMapper.toUsuario(usuarioDto);

      return ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, usuarioResponse);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuario"));
    }
  }

  @Override
  public Result<Void> agregarUsuario(UsuarioResponse usuarioResponse) {
    try {
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(usuarioResponse);

      repository.addUsuario(usuarioDto);

      return ResultBuilder.buildSuccess("Usuario insertado", null);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al agregar usuario"));
    }
  }

  @Override
  public Result<Void> actualizarUsuario(UsuarioResponse usuarioResponse) {
    try {
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(usuarioResponse);

      repository.updateUsuario(usuarioDto);

      return ResultBuilder.buildSuccess("Usuario actualizado", null);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al actualizar usuario"));
    }
  }

  @Override
  public Result<Void> eliminarUsuario(String folio) {
    Number code = repository.deleteUsuario(folio);

    if (Objects.isNull(code) || code.intValue() == 0) {
      throw new Error404(List.of("Usuario no encontrado"));
    }

    return ResultBuilder.buildSuccess("Usuario eliminado", null);
  }

}
