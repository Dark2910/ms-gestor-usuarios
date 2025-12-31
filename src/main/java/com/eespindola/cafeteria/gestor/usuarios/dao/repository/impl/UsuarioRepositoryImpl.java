package com.eespindola.cafeteria.gestor.usuarios.dao.repository.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error500;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.util.DataBaseUtil;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;
import com.eespindola.cafeteria.gestor.usuarios.util.FolioGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  UsuarioRepositoryImpl(
          @Qualifier(DataBaseUtil.HIKARI_DATA_SOURCE) DataSource dataSource
//          @Qualifier(DataBaseUtil.HIKARI_CONNECTION) JdbcTemplate jdbcTemplate
  ) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
//    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<UsuarioDto> getAll() {
    final String USUARIO_GET_ALL = "SELECT * FROM VW_Usuario";
    try (
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement callableStatement = connection.prepareCall(USUARIO_GET_ALL);
    ) {
      ResultSet resultSet = callableStatement.executeQuery();
      List<UsuarioDto> usuarioDtoList = new ArrayList<>();

      while (resultSet.next()) {
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .idUsuario(resultSet.getInt("IDUSUARIO"))
                .folioId(resultSet.getString("FOLIO"))
                .nombre(resultSet.getString("NOMBRE"))
                .apellidoPaterno(resultSet.getString("APELLIDOPATERNO"))
                .apellidoMaterno(resultSet.getString("APELLIDOMATERNO"))
                .fechaNacimiento(resultSet.getDate("FECHANACIMIENTO").toLocalDate())
                .username(resultSet.getString("USERNAME"))
                .email(resultSet.getString("EMAIL"))
                .password(resultSet.getString("PASSWORD"))
                .status(resultSet.getString("STATUS"))
                .build();
        usuarioDtoList.add(usuarioDto);
      }
      return usuarioDtoList;
    } catch (SQLException e) {
      throw new Error500(List.of("Error al insertar en DB"));
    }
  }

  @Override
  public UsuarioDto getByFolio(String folio) {
    final String USUARIO_GET_BY_FOLIO =
            "SELECT IDUSUARIO, FOLIO, NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO, FECHANACIMIENTO, USERNAME, EMAIL, PASSWORD, STATUS " +
                    "FROM USUARIO " +
                    "WHERE FOLIO = ?";

//    jdbcTemplate.query(USUARIO_GET_BY_FOLIO, new UsuarioMapper(), folio).getFirst();

    return jdbcTemplate.query(USUARIO_GET_BY_FOLIO, (rs, rowNum) -> UsuarioDto.builder()
            .idUsuario(rs.getInt("IDUSUARIO"))
            .folioId(rs.getString("FOLIO"))
            .nombre(rs.getString("NOMBRE"))
            .apellidoPaterno(rs.getString("APELLIDOPATERNO"))
            .apellidoMaterno(rs.getString("APELLIDOMATERNO"))
            .fechaNacimiento(rs.getDate("FECHANACIMIENTO").toLocalDate())
            .username(rs.getString("USERNAME"))
            .email(rs.getString("EMAIL"))
            .password(rs.getString("PASSWORD"))
            .status(rs.getString("STATUS"))
            .build(), folio).getFirst();
  }

  @Override
  public void addUsuario(UsuarioDto usuarioDto) {
    final String USUARIO_INSERT = "{CALL SP_UsuarioInsert(?,?,?,?,?,?,?,?)}";
    try (
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement callableStatement = connection.prepareCall(USUARIO_INSERT);
    ) {
      callableStatement.setString("pFolio", FolioGenerator.getFolio());
      callableStatement.setString("pNombre", usuarioDto.getNombre());
      callableStatement.setString("pApellidoPaterno", usuarioDto.getApellidoPaterno());
      callableStatement.setString("pApellidoMaterno", usuarioDto.getApellidoMaterno());
      callableStatement.setString("pFechaNacimiento", FechasUtil.toString(FechasUtil.FORMAT_1, usuarioDto.getFechaNacimiento()));
      callableStatement.setString("pUsername", usuarioDto.getUsername());
      callableStatement.setString("pEmail", usuarioDto.getEmail());
      callableStatement.setString("pPassword", usuarioDto.getPassword());

      callableStatement.executeQuery();
    } catch (SQLException e) {
      throw new Error500(List.of("Error al insertar en DB"));
    }
  }

  @Override
  public void updateUsuario(UsuarioDto usuarioDto) {
    final String USUARIO_UPDATE = "SP_UsuarioUpdate";
    try {
      SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
              .withProcedureName(USUARIO_UPDATE)
              .declareParameters(
                      new SqlParameter("pFolio", Types.NUMERIC),
                      new SqlParameter("pNombre", Types.VARCHAR),
                      new SqlParameter("pApellidoPaterno", Types.VARCHAR),
                      new SqlParameter("pApellidoMaterno", Types.VARCHAR),
                      new SqlParameter("pFechaNacimiento", Types.DATE),
                      new SqlParameter("pUsername", Types.VARCHAR),
                      new SqlParameter("pEmail", Types.VARCHAR),
                      new SqlParameter("pPassword", Types.VARCHAR),
                      new SqlParameter("pStatus", Types.NUMERIC),
                      new SqlOutParameter("PRESULTADO", Types.NUMERIC)
              );

      SqlParameterSource params = new MapSqlParameterSource()
              .addValue("pFolio", usuarioDto.getFolioId())
              .addValue("pNombre", usuarioDto.getNombre())
              .addValue("pApellidoPaterno", usuarioDto.getApellidoPaterno())
              .addValue("pApellidoMaterno", usuarioDto.getApellidoMaterno())
              .addValue("pFechaNacimiento", usuarioDto.getFechaNacimiento())
              .addValue("pUsername", usuarioDto.getUsername())
              .addValue("pEmail", usuarioDto.getEmail())
              .addValue("pPassword", usuarioDto.getPassword())
              .addValue("pStatus", usuarioDto.getStatus());

      Map<String, Object> outParams = simpleJdbcCall.execute(params);
      Number resultado = (Number) outParams.get("PRESULTADO");

    } catch (Throwable e) {
      throw new Error500(List.of("Error al insertar en DB"));
    }
  }

  @Override
  public Number deleteUsuario(UsuarioDto usuarioDto) {
    final String USUARIO_DELETE = "{CALL SP_UsuarioDelete(?,?)}";

    return jdbcTemplate.execute((Connection connection) -> {
      try(CallableStatement callableStatement = connection.prepareCall(USUARIO_DELETE)){
        callableStatement.setString("pFolio", usuarioDto.getFolioId());
        callableStatement.registerOutParameter("pResultado", Types.NUMERIC);
        callableStatement.execute();

        return (Number) callableStatement.getObject("pResultado");
      } catch (Throwable e){
        throw new Error500(List.of("Error al eliminar usuario en DB"));
      }
    });
  }

}
