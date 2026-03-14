package com.eespindola.cafeteria.gestor.usuarios.controller;

import com.eespindola.cafeteria.gestor.usuarios.annotations.AroundAop;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  private final UsuarioService service;

  @Autowired
  UsuarioController(
          UsuarioService usuarioService
  ) {
    this.service = usuarioService;
  }

  @AroundAop
  @GetMapping("/get-all")
  public ResponseEntity<Result<UsuarioResponse>> getAllController() {
    Result<UsuarioResponse> result = service.consultarUsuarios();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{folio}")
  public ResponseEntity<Result<UsuarioResponse>> getByFolioController(
          @PathVariable String folio
  ) {
    Result<UsuarioResponse> result = service.consultarPorFolio(folio);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/add-user")
  public ResponseEntity<Result<Void>> addUsuarioController(
          @Valid @RequestBody UsuarioResponse usuarioResponse
  ) {
    Result<Void> result = service.agregarUsuario(usuarioResponse);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/update-user")
  public ResponseEntity<Result<Void>> updateUsuarioController(
          @RequestBody UsuarioResponse usuarioResponse
  ) {
    Result<Void> result = service.actualizarUsuario(usuarioResponse);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @DeleteMapping("/delete-user")
  public ResponseEntity<Result<Void>> deleteUsuarioController(
          @RequestBody Map<String, String> body
  ) {
    String folio = body.get("folio");
    Result<Void> result = service.eliminarUsuario(folio);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
