package com.eespindola.cafeteria.gestor.usuarios.controller;

import com.eespindola.cafeteria.gestor.usuarios.model.EntryRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioService;
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
          UsuarioService iUsuarioService
  ) {
    this.service = iUsuarioService;
  }

  @GetMapping("/get-all")
  public ResponseEntity<Result<UsuarioRequest>> getAllController() {
    Result<UsuarioRequest> result = service.consultarUsuarios();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{folio}")
  public ResponseEntity<Result<UsuarioRequest>> getByFolioController(
          @PathVariable String folio
  ) {
    Result<UsuarioRequest> result = service.consultarPorFolio(folio);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/add-user")
  public ResponseEntity<Result<Void>> addUsuarioController(
          @RequestBody String addUsuarioRequest
  ) {
    Result<Void> result = service.agregarUsuario(addUsuarioRequest, new EntryRequest<>(new UsuarioRequest()));
    return ResponseEntity.ok(result);
  }

  @PutMapping("/update-user")
  public ResponseEntity<Result<Void>> updateUsuarioController(
          @RequestBody String updateUsuarioRequest
  ) {
    Result<Void> result = service.actualizarUsuario(updateUsuarioRequest, new EntryRequest<>(new UsuarioRequest()));
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
