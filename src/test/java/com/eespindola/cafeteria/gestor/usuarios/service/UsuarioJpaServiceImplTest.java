package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.UsuarioJpa;
import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.service.impl.UsuarioJpaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioJpaServiceImplTest {

  @InjectMocks
  private UsuarioJpaServiceImpl service;

  @Mock
  private UsuarioJpa repository;

  @Test
  public void consultaUsuarioJpaTest(){
    // Arrange
    UsuarioEntity usuarioEntity = new UsuarioEntity();
    makeUsuarioEntityMock(usuarioEntity);
    when(repository.findAll()).thenReturn(List.of(usuarioEntity));
    // Act
    Result<Usuario> result = service.consultaUsuariosJpa();
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void consultarUsuariosTest_Error() {
    // Arrange
    when(repository.findAll()).thenReturn(null);
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.consultaUsuariosJpa();
    });
    // Assert
    Assertions.assertEquals("Error al consultar usuarios", error404.getDescription().getFirst());
  }

  @Test
  void consultarPorFolioJpaTest(){
    // Arrange
    UsuarioEntity usuarioEntity = new UsuarioEntity();
    makeUsuarioEntityMock(usuarioEntity);

    Optional<UsuarioEntity> optionalUsuario = Optional.of(usuarioEntity);

    when(repository.findByFolio(anyString())).thenReturn(optionalUsuario);
    // Act
    Result<Usuario> result = service.consultarPorFolioJpa(usuarioEntity.getFolio());
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void consultarPorFolioJpaTest_Test() {
    // Arrange
    Optional<UsuarioEntity> optionalUsuario = Optional.empty();
    when(repository.findByFolio(anyString())).thenReturn(optionalUsuario);
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.consultarPorFolioJpa(null);
    });
    // Assert
    Assertions.assertEquals("Error al consultar usuario", error404.getDescription().getFirst());
  }

  private void makeUsuarioEntityMock(UsuarioEntity usuarioEntity) {
    usuarioEntity.setIdUsuario(123);
    usuarioEntity.setFolio("folioTest");
    usuarioEntity.setNombre("nombreTest");
    usuarioEntity.setApellidoPaterno("apellidoPaternoTest");
    usuarioEntity.setApellidoMaterno("apellidoMaternoTest");
    usuarioEntity.setFechaNacimiento(LocalDate.now());
    usuarioEntity.setUsername("usernameTest");
    usuarioEntity.setEmail("emailTest");
    usuarioEntity.setPassword("passwordTest");
    usuarioEntity.setStatus("statusTest");
  }

}
