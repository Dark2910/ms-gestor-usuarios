package com.eespindola.cafeteria.gestor.usuarios.mapper;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;

public class UsuarioJpaMapper {

  public static UsuarioResponse toUsuario(UsuarioEntity usuarioEntity) {
    return UsuarioResponse.builder()
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

  public static UsuarioEntity toUsuarioEntity(UsuarioResponse usuarioResponse) {
    return UsuarioEntity.builder()
            .idUsuario(usuarioResponse.getIdUsuario())
            .folio(usuarioResponse.getFolioId())
            .nombre(usuarioResponse.getNombre())
            .apellidoPaterno(usuarioResponse.getApellidoPaterno())
            .apellidoMaterno(usuarioResponse.getApellidoMaterno())
            .fechaNacimiento(FechasUtil.toLocalDate(FechasUtil.FORMAT_1, usuarioResponse.getFechaNacimiento()))
            .username(usuarioResponse.getUsername())
            .email(usuarioResponse.getEmail())
            .password(usuarioResponse.getPassword())
            .status(usuarioResponse.getStatus())
            .build();
  }

  private UsuarioJpaMapper() {
    throw new IllegalArgumentException("Util class");
  }

}
