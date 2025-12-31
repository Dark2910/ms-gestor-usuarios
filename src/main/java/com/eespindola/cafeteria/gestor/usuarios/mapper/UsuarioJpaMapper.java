package com.eespindola.cafeteria.gestor.usuarios.mapper;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;

public class UsuarioJpaMapper {

  public static Usuario toUsuario(UsuarioEntity usuarioEntity) {
    return Usuario.builder()
            .idUsuario(usuarioEntity.getIdUsuario())
            .folioId(usuarioEntity.getFolio())
            .nombre(usuarioEntity.getNombre())
            .apellidoPaterno(usuarioEntity.getApellidoPaterno())
            .apellidoMaterno(usuarioEntity.getApellidoMaterno())
            .fechaNacimiento(FechasUtil.toString(FechasUtil.FORMAT_1, usuarioEntity.getFechaNacimiento()))
            .username(usuarioEntity.getUsername())
            .email(usuarioEntity.getEmail())
            .password(usuarioEntity.getPassword())
            .status(usuarioEntity.getStatus())
            .build();
  }

  public static UsuarioEntity toUsuarioEntity(Usuario usuario) {
    return UsuarioEntity.builder()
            .idUsuario(usuario.getIdUsuario())
            .folio(usuario.getFolioId())
            .nombre(usuario.getNombre())
            .apellidoPaterno(usuario.getApellidoPaterno())
            .apellidoMaterno(usuario.getApellidoMaterno())
            .fechaNacimiento(FechasUtil.toLocalDate(FechasUtil.FORMAT_1, usuario.getFechaNacimiento()))
            .username(usuario.getUsername())
            .email(usuario.getEmail())
            .password(usuario.getPassword())
            .status(usuario.getStatus())
            .build();
  }

  private UsuarioJpaMapper() {
    throw new IllegalArgumentException("Util class");
  }

}
