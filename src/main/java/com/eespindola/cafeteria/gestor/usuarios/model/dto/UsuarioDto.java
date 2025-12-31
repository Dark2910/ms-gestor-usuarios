package com.eespindola.cafeteria.gestor.usuarios.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

  private int idUsuario;
  private String folioId;
  private String nombre;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private LocalDate fechaNacimiento;
  private String username;
  private String email;
  private String password;
  private String status;

}
