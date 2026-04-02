package com.eespindola.cafeteria.gestor.usuarios.service;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error404;
import com.eespindola.cafeteria.gestor.usuarios.model.EntryRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
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
public class UsuarioRequestServiceImplTest {

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
    Result<UsuarioRequest> result = service.consultarUsuarios();
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
    Assertions.assertEquals("Error al consultar usuarios - Business", error404.getDescription().getFirst());
  }

  @Test
  void consultarPorFolioTest() {
    // Arrange
    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);
    when(repository.getByFolio(anyString())).thenReturn(usuarioDto);
    // Act
    Result<UsuarioRequest> result = service.consultarPorFolio(usuarioDto.getFolioId());
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
    Assertions.assertEquals("Error al consultar usuario - Business", error404.getDescription().getFirst());
  }

  @Test
  void agregarUsuarioTest() {
    // Arrange
//    UsuarioRequest usuarioRequest = makeUserMock();

    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);

    doNothing().when(repository).addUsuario(usuarioDto);
    // Act
    Result<Void> result = service.agregarUsuario("", new EntryRequest<>(makeUserMock()));
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void agregarUsuarioTest_Error() {
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.agregarUsuario("", new EntryRequest<>(null));
    });
    // Assert
    Assertions.assertNotNull("Error al agregar usuario", error404.getDescription().getFirst());
  }

  @Test
  void actualizarUsuarioTest() {
    // Arrange
//    UsuarioRequest usuarioRequest = makeUserMock();

    UsuarioDto usuarioDto = new UsuarioDto();
    makeUserDtoMock(usuarioDto);

    doNothing().when(repository).updateUsuario(usuarioDto);
    // Act
    Result<Void> result = service.actualizarUsuario("", new EntryRequest<>(makeUserMock()));
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void actualizarUsuarioTest_Error() {
    // Act
    Error404 error404 = Assertions.assertThrows(Error404.class, () -> {
      service.actualizarUsuario("", new EntryRequest<>(null));
    });
    // Assert
    Assertions.assertNotNull("Error al actualizar usuario", error404.getDescription().getFirst());
  }

  @Test
  void eliminarUsuarioTest() {
    // Arrange
    doNothing().when(repository).deleteUsuario(anyString());
    // Act
    Result<Void> result = service.eliminarUsuario("folioTest");
    // Assert
    Assertions.assertNotNull(result);
  }

  @Test
  void eliminarUsuarioTest_Error() {
    // Act
    Error404 errorNull = Assertions.assertThrows(Error404.class, () -> {
      service.eliminarUsuario(null);
    });
    Error404 errorBlank = Assertions.assertThrows(Error404.class, () -> {
      service.eliminarUsuario("   ");
    });
    // Assert
    String mensajeEsperado = "Folio invalido - Business";
    Assertions.assertEquals(mensajeEsperado, errorNull.getDescription().getFirst());
    Assertions.assertEquals(mensajeEsperado, errorBlank.getDescription().getFirst());

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

  private UsuarioRequest makeUserMock() {
    return UsuarioRequest.builder()
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
