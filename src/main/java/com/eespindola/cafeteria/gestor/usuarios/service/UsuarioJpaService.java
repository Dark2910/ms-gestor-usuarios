package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;

public interface UsuarioJpaService {

  Result<Usuario> consultaUsuariosJpa();

  Result<Usuario> consultarPorFolioJpa(String folio);

}
