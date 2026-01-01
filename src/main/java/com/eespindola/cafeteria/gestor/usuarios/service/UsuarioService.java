package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;

public interface UsuarioService {

  Result<Usuario> consultarUsuarios();

  Result<Usuario> consultarPorFolio(String folio);

  Result<Void> agregarUsuario(Usuario usuario);

  Result<Void> actualizarUsuario(Usuario usuario);

  Result<Void> eliminarUsuario(String folio);
}
