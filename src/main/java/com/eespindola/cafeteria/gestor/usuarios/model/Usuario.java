package com.eespindola.cafeteria.gestor.usuarios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

  @JsonProperty("idUsuario")
  private int idUsuario;

  @JsonProperty("folioId")
  private String folioId;

  @JsonProperty("nombre")
  @NotBlank(message = "nombre vacio o nulo")
  private String nombre;

  @JsonProperty("apellidoPaterno")
  @NotBlank(message = "apellidoPaterno vacio o nulo")
  private String apellidoPaterno;

  @JsonProperty("apellidoMaterno")
  @NotBlank(message = "apellidoMaterno vacio o nulo")
  private String apellidoMaterno;

  @JsonProperty("fechaNacimiento")
  @NotBlank(message = "fechaNacimiento vacio o nulo")
  private String fechaNacimiento;

  @JsonProperty("username")
  @NotBlank(message = "username vacio o nulo")
  private String username;

  @JsonProperty("email")
  @NotBlank(message = "email vacio o nulo")
  @Email(message = "email no valido")
  private String email;

  @JsonProperty("password")
  @NotBlank(message = "password vacio o nulo")
  private String password;

  @JsonProperty("status")
  private String status;

}
