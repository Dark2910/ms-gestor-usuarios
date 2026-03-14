package com.eespindola.cafeteria.gestor.usuarios.controller;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario-jpa")
public class UsuarioJpaController {

  private final UsuarioJpaService service;

  @Autowired
  UsuarioJpaController(
          UsuarioJpaService usuarioJpaService
  ) {
    this.service = usuarioJpaService;
  }

  @GetMapping("/get-all")
  public ResponseEntity<Result<UsuarioResponse>> getAllJpaController() {
    Result<UsuarioResponse> result = service.consultaUsuariosJpa();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{folio}")
  public ResponseEntity<Result<UsuarioResponse>> getByFolioJpaController(
          @PathVariable String folio
  ) {
    Result<UsuarioResponse> result = service.consultarPorFolioJpa(folio);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
