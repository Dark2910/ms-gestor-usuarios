package com.eespindola.cafeteria.gestor.usuarios.dao.repository.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error500;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.util.Constantes;
import com.eespindola.cafeteria.gestor.usuarios.util.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Slf4j
@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

  private final JdbcTemplate jdbcTemplate;
  private final String SCHEMA;
  private final String PACKAGE;

  @Autowired
  UsuarioRepositoryImpl(
//          @Qualifier(DataBaseUtil.HIKARI_CONNECTION) JdbcTemplate jdbcTemplate
          @Qualifier(DataBaseUtil.HIKARI_DATA_SOURCE) DataSource dataSource,
          @Value("${oracle.usuario.schema}") String usuarioSchema,
          @Value("${oracle.usuario.package}") String usuarioPackage
  ) {
//    this.jdbcTemplate = jdbcTemplate;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.SCHEMA = usuarioSchema;
    this.PACKAGE = usuarioPackage;
  }

  @Override
  public List<UsuarioDto> getAll() {
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_GET_ALL, 3);
    try {
      return jdbcTemplate.execute(QUERY, (CallableStatementCallback<List<UsuarioDto>>) callableStatement -> {
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
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE,Constantes.SP_GET_BY_FOLIO, 4);
    try {
      return jdbcTemplate.execute(QUERY, (CallableStatementCallback<UsuarioDto>) callableStatement -> {
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
    final String SP_INSERT = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_INSERT, 9);
    try{
      jdbcTemplate.execute(SP_INSERT, (CallableStatementCallback<Integer>) callableStatement ->{

        callableStatement.setString(1, usuarioDto.getNombre());
        callableStatement.setString(2, usuarioDto.getApellidoPaterno());
        callableStatement.setString(3, usuarioDto.getApellidoMaterno());
        callableStatement.setDate(4, Date.valueOf(usuarioDto.getFechaNacimiento()));
        callableStatement.setString(5, usuarioDto.getUsername());
        callableStatement.setString(6, usuarioDto.getEmail());
        callableStatement.setString(7, usuarioDto.getPassword());

        callableStatement.registerOutParameter(8, Types.NUMERIC);
        callableStatement.registerOutParameter(9, Types.VARCHAR);

        callableStatement.execute();

        Integer code = callableStatement.getInt(8);
        String message = callableStatement.getString(9);

        System.out.println("Código: " + code + ", Mensaje: " + message);
        return code;
      });
    }catch (Throwable e){
      throw new Error500(List.of("Error al insertar en DB"));
    }
  }

  @Override
  public void updateUsuario(UsuarioDto usuarioDto) {
    final String SP_UPDATE = "EESPINDOLAORQUESTADOR.PKG_USUARIO.SP_UsuarioUpdate";
    try {
      SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
              .withProcedureName(SP_UPDATE)
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
  public Number deleteUsuario(String folio) {
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_DELETE, 3);

    return jdbcTemplate.execute((Connection cnn) -> {
//      try (OracleCallableStatement callableStatement = cnn.prepareCall(QUERY).unwrap(OracleCallableStatement.class)) {
      try (CallableStatement callableStatement = cnn.prepareCall(QUERY)) {
        callableStatement.setString(1, folio);
        callableStatement.registerOutParameter(2, OracleTypes.NUMERIC); // Codigo
        callableStatement.registerOutParameter(3, OracleTypes.VARCHAR); // Message

        callableStatement.execute();

        Integer code = callableStatement.getInt(2);
        String message = callableStatement.getString(3);

        return code;
      }catch (Throwable e){
        throw new Error500(List.of("Error al eliminar usuario en DB"));
      }
    });
  }

}
