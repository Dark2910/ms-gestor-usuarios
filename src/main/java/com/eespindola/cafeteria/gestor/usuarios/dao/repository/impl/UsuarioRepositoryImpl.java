package com.eespindola.cafeteria.gestor.usuarios.dao.repository.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error500;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.util.DataBaseUtil;
import com.eespindola.cafeteria.gestor.usuarios.util.FechasUtil;
import com.eespindola.cafeteria.gestor.usuarios.util.FolioGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Slf4j
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
    final String USUARIO_GET_ALL = "{CALL SP_UsuarioGetAll(?, ?, ?)}";
    try {
      return jdbcTemplate.execute(USUARIO_GET_ALL, (CallableStatementCallback<List<UsuarioDto>>) callableStatement -> {
        callableStatement.registerOutParameter("pCodigo", OracleTypes.NUMERIC);
        callableStatement.registerOutParameter("pMensaje", OracleTypes.VARCHAR);
        callableStatement.registerOutParameter("pCursor", OracleTypes.CURSOR);
        callableStatement.execute();

        Integer code = callableStatement.getInt("pCodigo");
        String message = callableStatement.getString("pMensaje");

        List<UsuarioDto> usuarioDtoList = new LinkedList<>();
        UsuarioDto usuarioDto;

        try(ResultSet resultSet = (ResultSet) callableStatement.getObject("pCursor")){
          while (resultSet.next()){
            usuarioDto = UsuarioDto.builder()
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
        }
        return usuarioDtoList;
      });
    } catch (Throwable e) {
      throw new Error500(List.of("Error al consultar usuarios."));
    }
  }

  @Override
  public UsuarioDto getByFolio(String folio) {
    final String USUARIO_GET_BY_FOLIO = "{CALL SP_UsuarioGetByFolio(?, ?, ?, ?)}";
    try {
      return jdbcTemplate.execute(USUARIO_GET_BY_FOLIO, (CallableStatementCallback<UsuarioDto>) callableStatement -> {
        callableStatement.setString(1, folio);
        callableStatement.registerOutParameter(2, OracleTypes.NUMERIC);
        callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
        callableStatement.registerOutParameter(4, OracleTypes.CURSOR);
        callableStatement.execute();

        Integer code = callableStatement.getInt(2);
        String message = callableStatement.getString(3);

        UsuarioDto usuarioDto = new UsuarioDto();

        try (ResultSet resultSet = (ResultSet) callableStatement.getObject(4)) {
          if (resultSet.next()) {
            usuarioDto = UsuarioDto.builder()
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
          }
        }
        return usuarioDto;
      });
    } catch (Throwable e) {
      throw new Error500(List.of("Error al consultar usuario."));
    }
  }

  @Override
  public void addUsuario(UsuarioDto usuarioDto) {
    final String USUARIO_INSERT = "{CALL SP_UsuarioInsert(?,?,?)}";
    try{
      jdbcTemplate.execute(USUARIO_INSERT, (CallableStatementCallback<Integer>) callableStatement ->{

        OracleConnection oracleConnection = callableStatement.getConnection().unwrap(OracleConnection.class);
        Object[] atributos = new Object[]{
                usuarioDto.getFolioId(),
                usuarioDto.getNombre(),
                usuarioDto.getApellidoPaterno(),
                usuarioDto.getApellidoMaterno(),
                Date.valueOf( usuarioDto.getFechaNacimiento()),
                usuarioDto.getUsername(),
                usuarioDto.getEmail(),
                usuarioDto.getPassword(),
                usuarioDto.getStatus()
        };
        Struct structUsuarioType = oracleConnection.createStruct("EESPINDOLAORQUESTADOR.USUARIO_TYPE", atributos);
        callableStatement.setObject(1, structUsuarioType, OracleTypes.STRUCT);

        callableStatement.registerOutParameter(2, Types.NUMERIC);
        callableStatement.registerOutParameter(3, Types.VARCHAR);
        callableStatement.execute();

        Integer code = callableStatement.getInt(2);
        String message = callableStatement.getString(3);

        System.out.println("Código: " + code + ", Mensaje: " + message);
        return code;
      });
    }catch (Throwable e){
      throw new Error500(List.of("Error al insertar en DB"));
    }



//    try (
//            Connection connection = jdbcTemplate.getDataSource().getConnection();
//            CallableStatement callableStatement = connection.prepareCall(USUARIO_INSERT);
//    ) {
//      callableStatement.setString("pFolio", FolioGenerator.getFolio());
//      callableStatement.setString("pNombre", usuarioDto.getNombre());
//      callableStatement.setString("pApellidoPaterno", usuarioDto.getApellidoPaterno());
//      callableStatement.setString("pApellidoMaterno", usuarioDto.getApellidoMaterno());
//      callableStatement.setString("pFechaNacimiento", FechasUtil.toString(FechasUtil.FORMAT_1, usuarioDto.getFechaNacimiento()));
//      callableStatement.setString("pUsername", usuarioDto.getUsername());
//      callableStatement.setString("pEmail", usuarioDto.getEmail());
//      callableStatement.setString("pPassword", usuarioDto.getPassword());
//
//      callableStatement.executeQuery();
//    } catch (SQLException e) {
//      throw new Error500(List.of("Error al insertar en DB"));
//    }
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
      try (CallableStatement callableStatement = connection.prepareCall(USUARIO_DELETE)) {
        callableStatement.setString("pFolio", usuarioDto.getFolioId());
        callableStatement.registerOutParameter("pResultado", Types.NUMERIC);
        callableStatement.execute();

        return (Number) callableStatement.getObject("pResultado");
      } catch (Throwable e) {
        throw new Error500(List.of("Error al eliminar usuario en DB"));
      }
    });
  }

}
