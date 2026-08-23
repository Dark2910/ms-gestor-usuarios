package com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
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

  // ==========================================
  // 2. CONSTRUCTORES
  // ==========================================

  public UsuarioEntity() {}

  private UsuarioEntity(UsuarioEntityBuilder builder) {
    this.idUsuario = builder. idUsuario;
    this.folio = builder. folio;
    this.nombre = builder. nombre;
    this.apellidoPaterno = builder. apellidoPaterno;
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

  public static UsuarioEntityBuilder builder(){
    return new UsuarioEntityBuilder();
  }

  // ==========================================
  // 4. GETTERS Y SETTERS
  // ==========================================

  public int getIdUsuario() {
    return idUsuario;
  }
  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getFolio() {
    return folio;
  }
  public void setFolio(String folio) {
    this.folio = folio;
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

  public static class UsuarioEntityBuilder{
    private int idUsuario;
    private String folio;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String username;
    private String email;
    private String password;
    private String status;


    public UsuarioEntityBuilder idUsuario(int idUsuario) {
      this.idUsuario = idUsuario;
      return this;
    }

    public UsuarioEntityBuilder folio(String folio) {
      this.folio = folio;
      return this;
    }

    public UsuarioEntityBuilder nombre(String nombre) {
      this.nombre = nombre;
      return this;
    }

    public UsuarioEntityBuilder apellidoPaterno(String apellidoPaterno) {
      this.apellidoPaterno = apellidoPaterno;
      return this;
    }

    public UsuarioEntityBuilder apellidoMaterno(String apellidoMaterno) {
      this.apellidoMaterno = apellidoMaterno;
      return this;
    }

    public UsuarioEntityBuilder fechaNacimiento(LocalDate fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
      return this;
    }

    public UsuarioEntityBuilder username(String username) {
      this.username = username;
      return this;
    }

    public UsuarioEntityBuilder email(String email) {
      this.email = email;
      return this;
    }

    public UsuarioEntityBuilder password(String password) {
      this.password = password;
      return this;
    }

    public UsuarioEntityBuilder status(String status) {
      this.status = status;
      return this;
    }

    public UsuarioEntity build(){
      return new UsuarioEntity(this);
    }

  }
}
