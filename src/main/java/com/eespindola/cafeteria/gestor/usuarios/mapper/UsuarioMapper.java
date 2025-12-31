package com.eespindola.cafeteria.gestor.usuarios.mapper;

import com.eespindola.cafeteria.gestor.usuarios.model.Usuario;
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

  public static Usuario toUsuario(UsuarioDto usuarioDto) {
    return Usuario.builder()
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

  public static UsuarioDto toUsuarioDto(Usuario usuario) {
    return UsuarioDto.builder()
            .idUsuario(usuario.getIdUsuario())
            .folioId(usuario.getFolioId())
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

  private UsuarioMapper(){
    throw new IllegalArgumentException("Util class");
  }

}
