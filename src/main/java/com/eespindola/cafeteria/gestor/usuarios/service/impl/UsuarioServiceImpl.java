package com.eespindola.cafeteria.gestor.usuarios.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.mapper.UsuarioMapper;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioService;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository repository;

  @Autowired
  UsuarioServiceImpl(
          UsuarioRepository usuarioRepository
  ) {
    this.repository = usuarioRepository;
  }

  @Override
  public Result<Usuario> consultarUsuarios() {
    try {
      List<UsuarioDto> usuarioDtoList = repository.getAll();

      List<Usuario> usuarioList = usuarioDtoList.stream()
              .map(UsuarioMapper::toUsuario)
              .collect(Collectors.toCollection(ArrayList::new));

      return ResultBuilder.buildSuccess("Usuarios consultados", usuarioList);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuarios"));
    }
  }

  @Override
  public Result<Usuario> consultarPorFolio(String folio) {
    try {
      UsuarioDto usuarioDto = repository.getByFolio(folio);

      Usuario usuario = UsuarioMapper.toUsuario(usuarioDto);

      return ResultBuilder.buildSuccess("Usuario consultado", usuario);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al consultar usuario"));
    }
  }

  @Override
  public Result<Void> agregarUsuario(Usuario usuario) {
    try {
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(usuario);

      repository.addUsuario(usuarioDto);

      return ResultBuilder.buildSuccess("Usuario insertado", null);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al agregar usuario"));
    }
  }

  @Override
  public Result<Void> actualizarUsuario(Usuario usuario) {
    try {
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(usuario);

      repository.updateUsuario(usuarioDto);

      return ResultBuilder.buildSuccess("Usuario actualizado", null);
    } catch (RuntimeException e) {
      throw new Error404(List.of("Error al agregar usuario"));
    }
  }

  @Override
  public Result<Void> eliminarUsuario(Usuario usuario) {
    try{
      UsuarioDto usuarioDto = UsuarioMapper.toUsuarioDto(usuario);

      repository.deleteUsuario(usuarioDto);

      return ResultBuilder.buildSuccess("Usuario eliminado", null);
    }catch (RuntimeException e){
      throw new Error404(List.of("Error al eliminar usuario"));
    }
  }

}
