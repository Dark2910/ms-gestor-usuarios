package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;

public interface UsuarioJpaService {

  Result<UsuarioRequest> consultaUsuariosJpa();
  Result<UsuarioRequest> consultarPorFolioJpa(String folio);

}
