package com.eespindola.cafeteria.gestor.usuarios.dao.repository;

import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;

import java.util.List;

public interface UsuarioRepository {

  List<UsuarioDto> getAll();

  UsuarioDto getByFolio(String folio);

  void addUsuario(UsuarioDto usuarioDto);

  void updateUsuario(UsuarioDto usuarioDto);

  Number deleteUsuario(String folio);
}
