package com.eespindola.cafeteria.gestor.usuarios.mapper;

import com.eespindola.cafeteria.gestor.usuarios.dao.jpa.entities.UsuarioEntity;
import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;

public class UsuarioJpaMapper {

  public static UsuarioRequest toUsuario(UsuarioEntity usuarioEntity) {
    return UsuarioRequest.builder()
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

  public static UsuarioEntity toUsuarioEntity(UsuarioRequest usuarioRequest) {
    return UsuarioEntity.builder()
            .idUsuario(usuarioRequest.getIdUsuario())
            .folio(usuarioRequest.getFolioId())
            .nombre(usuarioRequest.getNombre())
            .apellidoPaterno(usuarioRequest.getApellidoPaterno())
            .apellidoMaterno(usuarioRequest.getApellidoMaterno())
            .fechaNacimiento(FechasUtil.toLocalDate(FechasUtil.FORMAT_1, usuarioRequest.getFechaNacimiento()))
            .username(usuarioRequest.getUsername())
            .email(usuarioRequest.getEmail())
            .password(usuarioRequest.getPassword())
            .status(usuarioRequest.getStatus())
            .build();
  }

  private UsuarioJpaMapper() {
    throw new IllegalArgumentException("Util class");
  }

}
