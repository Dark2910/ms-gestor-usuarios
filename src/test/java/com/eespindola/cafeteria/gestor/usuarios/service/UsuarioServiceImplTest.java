package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.service.impl.UsuarioServiceImpl;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

  @InjectMocks
  private UsuarioServiceImpl service;

  @Mock
  private UsuarioRepository repository;

  @Test
  void consultarUsuariosTest() {
    // Arrange
    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);
    when(repository.getAll()).thenReturn(List.of(usuarioDto));
    // Act
    Result<Usuario> result = service.consultarUsuarios();
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void consultarUsuariosTest_Error() {
    // Arrange
    when(repository.getAll()).thenReturn(null);
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.consultarUsuarios();
    });
    // Assert
    Assertions.assertEquals("Error al consultar usuarios", error404.getDescription().getFirst());
  }

  @Test
  void consultarPorFolioTest() {
    // Arrange
    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);
    when(repository.getByFolio(anyString())).thenReturn(usuarioDto);
    // Act
    Result<Usuario> result = service.consultarPorFolio(usuarioDto.getFolioId());
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void consultarPorFolioTest_Error() {
    // Arrange
    when(repository.getByFolio(anyString())).thenReturn(null);
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.consultarPorFolio("");
    });
    // Assert
    Assertions.assertEquals("Error al consultar usuario", error404.getDescription().getFirst());
  }

  @Test
  void agregarUsuarioTest() {
    // Arrange
    Usuario usuario = makeUserMock();

    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);

    doNothing().when(repository).addUsuario(usuarioDto);
    // Act
    Result<Void> result = service.agregarUsuario(usuario);
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void agregarUsuarioTest_Error() {
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.agregarUsuario(null);
    });
    // Assert
    Assertions.assertNotNull("Error al agregar usuario", error404.getDescription().getFirst());
  }

  @Test
  void actualizarUsuarioTest() {
    // Arrange
    Usuario usuario = makeUserMock();

    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);

    doNothing().when(repository).updateUsuario(usuarioDto);
    // Act
    Result<Void> result = service.actualizarUsuario(usuario);
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void actualizarUsuarioTest_Error() {
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.actualizarUsuario(null);
    });
    // Assert
    Assertions.assertNotNull("Error al actualizar usuario", error404.getDescription().getFirst());
  }

  @Test
  void eliminarUsuarioTest() {
    // Arrange
    when(repository.deleteUsuario(anyString())).thenReturn(1);
    // Act
    Result<Void> result = service.eliminarUsuario("");
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void eliminarUsuarioTest_Error() {
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.eliminarUsuario("");
    });
    // Assert
    Assertions.assertEquals("Usuario no encontrado", error404.getDescription().getFirst());
  }

  private static void makeUserDtoMock(UsuarioDto usuarioDto) {
    usuarioDto.setIdUsuario(123);
    usuarioDto.setFolioId("folioTest");
    usuarioDto.setNombre("nombreTest");
    usuarioDto.setApellidoPaterno("apellidoPaternoTest");
    usuarioDto.setApellidoMaterno("apellidoMaternoTest");
    usuarioDto.setFechaNacimiento(LocalDate.now());
    usuarioDto.setUsername("usernameTest");
    usuarioDto.setEmail("emailTest");
    usuarioDto.setPassword("passwordTest");
    usuarioDto.setStatus("statusTest");
  }

  private Usuario makeUserMock() {
    return Usuario.builder()
            .idUsuario(123)
            .folioId("folioTest")
            .nombre("nombreTest")
            .apellidoPaterno("apellidoPaternoTest")
            .apellidoMaterno("apellidoMaternoTest")
            .fechaNacimiento(FechasUtil.toString(FechasUtil.FORMAT_1, LocalDate.now()))
            .username("usernameTest")
            .email("emailTest")
            .password("passwordTest")
            .status("statusTest")
            .build();
  }

}
