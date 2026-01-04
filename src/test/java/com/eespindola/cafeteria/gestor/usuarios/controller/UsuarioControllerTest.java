package com.eespindola.cafeteria.gestor.usuarios.controller;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioService;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        UsuarioController.class,
        UsuarioService.class
})
class UsuarioControllerTest {

  @Autowired
  private UsuarioController controller;

  @MockBean
  private UsuarioService service;

  @Test
  void getAllControllerTest() {
    // Arrange
    Result<Usuario> result =
            ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, List.of(new Usuario()));
    when(service.consultarUsuarios()).thenReturn(result);
    // Act
    ResponseEntity<Result<Usuario>> response = controller.getAllController();
    // Assert
    Assertions.assertNotNull(response);
  }

  @Test
  void getByFolioControllerTest(){
    // Arrange
    Result<Usuario> result =
            ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, new Usuario());
    when(service.consultarPorFolio(anyString())).thenReturn(result);
    // Act
    ResponseEntity<Result<Usuario>> response = controller.getByFolioController("");
    // Assert
    Assertions.assertNotNull(response);
  }

  @Test
  void addUsuarioControllerTest(){
    // Arrange
    Result<Void> result =
            ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, null);
    when(service.agregarUsuario(any(Usuario.class))).thenReturn(result);
    // Act
    ResponseEntity<Result<Void>> response = controller.addUsuarioController(new Usuario());
    // Assert
    Assertions.assertNotNull(response);
  }

  @Test
  void updateUsuarioControllerTest(){
    // Arrange
    Result<Void> result =
            ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, null);
    when(service.actualizarUsuario(any(Usuario.class))).thenReturn(result);
    // Act
    ResponseEntity<Result<Void>> response = controller.updateUsuarioController(new Usuario());
    // Assert
    Assertions.assertNotNull(response);
  }

  @Test
  void deleteUsuarioControllerTest(){
    // Arrange
    Result<Void> result =
            ResultBuilder.buildSuccess(ResultBuilder.ResultConstants.SUCCESS, null);
    when(service.eliminarUsuario(anyString())).thenReturn(result);

    Map<String, String> request = new HashMap<>();
    request.put("folio", "folioTest");
    // Act
    ResponseEntity<Result<Void>> response = controller.deleteUsuarioController(request);
    // Assert
    Assertions.assertNotNull(response);
  }

}
