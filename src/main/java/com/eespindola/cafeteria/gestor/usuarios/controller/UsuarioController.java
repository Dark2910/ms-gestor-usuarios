package com.eespindola.cafeteria.gestor.usuarios.controller;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
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

  @GetMapping("/get-all")
  public ResponseEntity<Result<Usuario>> getAllController() {
    Result<Usuario> result = service.consultarUsuarios();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{folio}")
  public ResponseEntity<Result<Usuario>> getByFolioController(
          @PathVariable String folio
  ) {
    Result<Usuario> result = service.consultarPorFolio(folio);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/add-user")
  public ResponseEntity<Result<Void>> addUsuarioController(
          @Valid @RequestBody Usuario usuario
  ) {
    Result<Void> result = service.agregarUsuario(usuario);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/update-user")
  public ResponseEntity<Result<Void>> updateUsuarioController(
          @RequestBody Usuario usuario
  ) {
    Result<Void> result = service.actualizarUsuario(usuario);
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
