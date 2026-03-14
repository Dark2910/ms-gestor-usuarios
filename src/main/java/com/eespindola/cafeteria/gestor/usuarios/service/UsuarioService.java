package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;

public interface UsuarioService {

  Result<UsuarioResponse> consultarUsuarios();
  Result<UsuarioResponse> consultarPorFolio(String folio);
  Result<Void> agregarUsuario(UsuarioResponse usuarioResponse);
  Result<Void> actualizarUsuario(UsuarioResponse usuarioResponse);
  Result<Void> eliminarUsuario(String folio);

}
