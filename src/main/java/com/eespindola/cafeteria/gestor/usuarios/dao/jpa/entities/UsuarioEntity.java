package com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuario")
public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idusuario", insertable = false)
  private int idUsuario;

  @Column(name = "folio")
  private String folio;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellidopaterno")
  private String apellidoPaterno;

  @Column(name = "apellidomaterno")
  private String apellidoMaterno;

  @Column(name = "fechanacimiento")
  private LocalDate fechaNacimiento;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "status")
  private String status;

}
