package com.eespindola.cafeteria.gestor.usuarios.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
@JsonInclude(
  JsonInclude.Include.NON_NULL
)
public class UsuarioRequest {

  @JsonProperty(
    "idUsuario"
  )
  private Integer idUsuario;

  @JsonProperty(
    "folioId"
  )
  private String folioId;

  @JsonProperty(
    "nombre"
  )
  @NotBlank(
          message = "nombre vacio o nulo"
  )
  private String nombre;

  @JsonProperty(
    "apellidoPaterno"
  )
  @NotBlank(
          message = "apellidoPaterno vacio o nulo"
  )
  private String apellidoPaterno;

  @JsonProperty(
    "apellidoMaterno"
  )
  @NotBlank(
          message = "apellidoMaterno vacio o nulo"
  )
  private String apellidoMaterno;

  @JsonProperty(
    "fechaNacimiento"
  )
  @NotBlank(
          message = "fechaNacimiento vacio o nulo"
  )
  private String fechaNacimiento;

  @JsonProperty(
    "username"
  )
  @NotBlank(
          message = "username vacio o nulo"
  )
  private String username;

  @JsonProperty(
    "email"
  )
  @NotBlank(
          message = "email vacio o nulo"
  )
  @Email(
          message = "email no valido"
  )
  private String email;

  @JsonProperty(
    "password"
  )
  @NotBlank(
          message = "password vacio o nulo"
  )
  private String password;

  @JsonProperty(
    "status"
  )
  private String status;

  // ==========================================
  // 2. CONSTRUCTORES
  // ==========================================

  public UsuarioRequest() {
  }

  private UsuarioRequest(UsuarioRequestBuilder builder) {
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

  public static UsuarioRequestBuilder builder() {
    return new UsuarioRequestBuilder();
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

  public String getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(String fechaNacimiento) {
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

  public static class UsuarioRequestBuilder {

    private Integer idUsuario;
    private String folioId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String username;
    private String email;
    private String password;
    private String status;

    public UsuarioRequestBuilder idUsuario(Integer idUsuario) {
      this.idUsuario = idUsuario;
      return this;
    }

    public UsuarioRequestBuilder folioId(String folioId) {
      this.folioId = folioId;
      return this;
    }

    public UsuarioRequestBuilder nombre(String nombre) {
      this.nombre = nombre;
      return this;
    }

    public UsuarioRequestBuilder apellidoPaterno(String apellidoPaterno) {
      this.apellidoPaterno = apellidoPaterno;
      return this;
    }

    public UsuarioRequestBuilder apellidoMaterno(String apellidoMaterno) {
      this.apellidoMaterno = apellidoMaterno;
      return this;
    }

    public UsuarioRequestBuilder fechaNacimiento(String fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
      return this;
    }

    public UsuarioRequestBuilder username(String username) {
      this.username = username;
      return this;
    }

    public UsuarioRequestBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UsuarioRequestBuilder password(String password) {
      this.password = password;
      return this;
    }

    public UsuarioRequestBuilder status(String status) {
      this.status = status;
      return this;
    }

    public UsuarioRequest build() {
      return new UsuarioRequest(this);
    }
  }
}
