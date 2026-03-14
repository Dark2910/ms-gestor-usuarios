package com.eespindola.cafeteria.gestor.usuarios.mapper;

import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioResponse;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;

public class UsuarioMapper {

//public class UsuarioMapper implements RowMapper<UsuarioDto> {
//
//  @Override
//  public UsuarioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//    return UsuarioDto.builder()
//            .idUsuario(rs.getInt(1))
//            .folioId(rs.getString(2))
//            .nombre(rs.getString(3))
//            .apellidoPaterno(rs.getString(4))
//            .apellidoMaterno(rs.getString(5))
//            .fechaNacimiento(FechasUtil.toString(FechasUtil.FORMAT_1, rs.getDate(6)))
//            .username(rs.getString(7))
//            .email(rs.getString(8))
//            .password(rs.getString(9))
//            .status(rs.getString(10))
//            .build();
//  }

  public static UsuarioResponse toUsuario(UsuarioDto usuarioDto) {
    return UsuarioResponse.builder()
            .idUsuario(usuarioDto.getIdUsuario())
            .folioId(usuarioDto.getFolioId())
            .nombre(usuarioDto.getNombre())
            .apellidoPaterno(usuarioDto.getApellidoPaterno())
            .apellidoMaterno(usuarioDto.getApellidoMaterno())
            .fechaNacimiento(FechasUtil.toString(FechasUtil.FORMAT_1, usuarioDto.getFechaNacimiento()))
            .username(usuarioDto.getUsername())
            .email(usuarioDto.getEmail())
            .password(usuarioDto.getPassword())
            .status(usuarioDto.getStatus())
            .build();
  }

  public static UsuarioDto toUsuarioDto(UsuarioResponse usuarioResponse) {
    return UsuarioDto.builder()
            .idUsuario(usuarioResponse.getIdUsuario())
            .folioId(usuarioResponse.getFolioId())
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

  private UsuarioMapper(){
    throw new IllegalArgumentException("Util class");
  }

}
