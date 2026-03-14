package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;

public interface UsuarioJpaService {

  Result<UsuarioResponse> consultaUsuariosJpa();
  Result<UsuarioResponse> consultarPorFolioJpa(String folio);

}
