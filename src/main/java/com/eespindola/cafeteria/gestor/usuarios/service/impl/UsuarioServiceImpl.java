package com.eespindola.cafeteria.gestor.usuarios.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.annotations.AroundMapperAop;
import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.mapper.UsuarioMapper;
import com.eespindola.cafeteria.gestor.usuarios.model.EntryRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
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
  public Result<UsuarioRequest> consultarUsuarios() {
    try {
      List<UsuarioDto> usuarioDtoList = repository.getAll();

      List<UsuarioRequest> usuarioRequestList = usuarioDtoList.stream()
              .map(UsuarioMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new));

      LOG.info("*** Consulta usuarios exitosa");
      return ResultBuilder.buildSuccess(ResultBuilder.SUCCESS, usuarioRequestList);
    }
    catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuarios - Business"));
    }
  }

  @Override
  public Result<UsuarioRequest> consultarPorFolio(String folio) {
    try {
      UsuarioDto usuarioDto = repository.getByFolio(folio);

      UsuarioRequest usuarioRequest = UsuarioMapper.toUsuario(usuarioDto);

      LOG.info("*** Consulta por folio exitosa");
      return ResultBuilder.buildSuccess(ResultBuilder.SUCCESS, usuarioRequest);
    }
    catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuario - Business"));
    }
  }

  @AroundMapperAop
  @Override
  public Result<Void> agregarUsuario(String request, EntryRequest<UsuarioRequest> entryRequest) {
    try {
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(entryRequest.getRequest());

      repository.addUsuario(usuarioDto);

      LOG.info("*** Usuario insertado");
      return ResultBuilder.buildSuccess("Usuario insertado", null);
    }
    catch (RuntimeException e) {
      throw new Error404(List.of("Error al agregar usuario - Business"));
    }
  }

  @AroundMapperAop
  @Override
  public Result<Void> actualizarUsuario(String request, EntryRequest<UsuarioRequest> entryRequest) {
    try {
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(entryRequest.getRequest());

      repository.updateUsuario(usuarioDto);

      LOG.info("*** Usuario actualizado");
      return ResultBuilder.buildSuccess("Usuario actualizado", null);
    }
    catch (RuntimeException e) {
      throw new Error404(List.of("Error al actualizar usuario - Business"));
    }
  }

  @Override
  public Result<Void> eliminarUsuario(String folio) {
    if(folio == null || folio.trim().isBlank()){
      throw new Error404(List.of("Folio invalido - Business"));
    }
    repository.deleteUsuario(folio);

    LOG.info("*** Usuario eliminado");
    return ResultBuilder.buildSuccess("Usuario eliminado", null);
  }

}
