package com.eespindola.cafeteria.gestor.usuarios.model.dto;

import java.time.LocalDate;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class UsuarioDto {

  private Integer idUsuario;
  private String folioId;
  private String nombre;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private LocalDate fechaNacimiento;
  private String username;
  private String email;
  private String password;
  private String status;

  // ==========================================
  // 2. CONSTRUCTORES
  // ==========================================

  public UsuarioDto() {
  }

  private UsuarioDto(UsuarioDtoBuilder builder) {
    this.idUsuario = builder.idUsuario;
    this.folioId = builder.folioId;
    this.nombre = builder.nombre;
    this.apellidoPaterno = builder.apellidoPaterno;
    this.apellidoMaterno = builder.apellidoMaterno;
    this.fechaNacimiento = builder.fechaNacimiento;
    this.username = builder.username;
    this.email = builder.email;
    this.password = builder.password;
    this.status = builder.status;
  }

  // ==========================================
  // 3. MÉTODOS ESTÁTICOS
  // ==========================================

  public static UsuarioDtoBuilder builder(){
    return new UsuarioDtoBuilder();
  }

  // ==========================================
  // 4. GETTERS Y SETTERS
  // ==========================================

  public Integer getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getFolioId() {
    return folioId;
  }

  public void setFolioId(String folioId) {
    this.folioId = folioId;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public String getApellidoMaterno() {
    return apellidoMaterno;
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  // ==========================================
  // 5. CLASE BUILDER
  // ==========================================

  public static class UsuarioDtoBuilder {

    private Integer idUsuario;
    private String folioId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String username;
    private String email;
    private String password;
    private String status;


    public UsuarioDtoBuilder idUsuario(Integer idUsuario) {
      this.idUsuario = idUsuario;
      return this;
    }

    public UsuarioDtoBuilder folioId(String folioId) {
      this.folioId = folioId;
      return this;
    }

    public UsuarioDtoBuilder nombre(String nombre) {
      this.nombre = nombre;
      return this;
    }

    public UsuarioDtoBuilder apellidoPaterno(String apellidoPaterno) {
      this.apellidoPaterno = apellidoPaterno;
      return this;
    }

    public UsuarioDtoBuilder apellidoMaterno(String apellidoMaterno) {
      this.apellidoMaterno = apellidoMaterno;
      return this;
    }

    public UsuarioDtoBuilder fechaNacimiento(LocalDate fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
      return this;
    }

    public UsuarioDtoBuilder username(String username) {
      this.username = username;
      return this;
    }

    public UsuarioDtoBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UsuarioDtoBuilder password(String password) {
      this.password = password;
      return this;
    }

    public UsuarioDtoBuilder status(String status) {
      this.status = status;
      return this;
    }

    public UsuarioDto build(){
      return new UsuarioDto(this);
    }

  }
}
