package com.eespindola.cafeteria.gestor.usuarios.mapper;

import com.eespindola.cafeteria.gestor.usuarios.model.UsuarioRequest;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioMapper {

//public class UsuarioMapper implements RowMapper<UsuarioDto> {

  public static UsuarioDto mapRow(ResultSet rs) throws SQLException {
    return UsuarioDto.builder()
            .folioId(rs.getString("FOLIO"))
            .nombre(rs.getString("NOMBRE"))
            .apellidoPaterno(rs.getString("APELLIDOPATERNO"))
            .apellidoMaterno(rs.getString("APELLIDOMATERNO"))
            .fechaNacimiento(rs.getDate("FECHANACIMIENTO").toLocalDate())
            .username(rs.getString("USERNAME"))
            .email(rs.getString("EMAIL"))
            .password(rs.getString("PASSWORD"))
            .status(rs.getString("STATUS"))
            .build();
  }

  public static UsuarioRequest toUsuario(UsuarioDto usuarioDto) {
    return UsuarioRequest.builder()
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

  public static UsuarioDto toUsuarioDto(UsuarioRequest usuarioRequest) {
    return UsuarioDto.builder()
            .idUsuario(usuarioRequest.getIdUsuario())
            .folioId(usuarioRequest.getFolioId())
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

  private UsuarioMapper() {
    throw new IllegalArgumentException("Util class");
  }

}
