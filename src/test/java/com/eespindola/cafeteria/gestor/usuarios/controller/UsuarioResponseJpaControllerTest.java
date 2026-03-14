package com.eespindola.cafeteria.gestor.usuarios.controller;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.service.UsuarioJpaService;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioResponseJpaControllerTest {

  @InjectMocks
  private UsuarioJpaController controller;

  @Mock
  private UsuarioJpaService service;

  @Test
  void getAllJpaController() {
    // Arrange
    Result<UsuarioResponse> result =
            ResultBuilder.buildSuccess(
                    ResultBuilder.ResultConstants.SUCCESS,
                    List.of(new UsuarioResponse())
            );
    when(service.consultaUsuariosJpa()).thenReturn(result);
    // Act
    ResponseEntity<Result<UsuarioResponse>> response = controller.getAllJpaController();
    // Assert
    Assertions.assertNotNull(response);
  }

  @Test
  void getByFolioJpaControllerTest() {
    // Arrange
    Result<UsuarioResponse> result =
            ResultBuilder.buildSuccess(
                    ResultBuilder.ResultConstants.SUCCESS,
                    List.of(new UsuarioResponse())
            );
    when(service.consultarPorFolioJpa(anyString())).thenReturn(result);
    // Act
    ResponseEntity<Result<UsuarioResponse>> response = controller.getByFolioJpaController("");
    // Assert
    Assertions.assertNotNull(response);
  }

}
