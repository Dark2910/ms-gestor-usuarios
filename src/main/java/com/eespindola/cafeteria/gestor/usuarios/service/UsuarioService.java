package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.model.EntryRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;

public interface UsuarioService {

  Result<UsuarioRequest> consultarUsuarios();

  Result<UsuarioRequest> consultarPorFolio(String folio);

  Result<Void> agregarUsuario(String request, EntryRequest<UsuarioRequest> entryRequest);

  Result<Void> actualizarUsuario(String request, EntryRequest<UsuarioRequest> entryRequest);

  Result<Void> eliminarUsuario(String folio);

}
