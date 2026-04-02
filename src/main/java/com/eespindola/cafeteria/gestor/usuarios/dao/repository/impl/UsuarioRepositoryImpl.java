package com.eespindola.cafeteria.gestor.usuarios.dao.repository.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error500;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.GenericRuntimeException;
import com.eespindola.cafeteria.gestor.usuarios.mapper.UsuarioMapper;
import com.eespindola.cafeteria.gestor.usuarios.model.dto.UsuarioDto;
import com.eespindola.cafeteria.gestor.usuarios.util.Constantes;
import com.eespindola.cafeteria.gestor.usuarios.util.DataBaseUtil;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {
  private static final Logger LOG = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);

  static final String P_FOLIO = "pFolio";
  static final String P_NOMBRE = "pNombre";
  static final String P_APELLIDO_PATERNO = "pApellidoPaterno";
  static final String P_APELLIDO_MATERNO = "pApellidoMaterno";
  static final String P_FECHA_NACIMIENTO = "pFechaNacimiento";
  static final String P_USERNAME = "pUsername";
  static final String P_EMAIL = "pEmail";
  static final String P_PASSWORD = "pPassword";
  static final String P_STATUS = "pStatus";
  static final String P_O_CODIGO = "pCodigo";
  static final String P_O_MENSAJE = "pMensaje";

  private final JdbcTemplate jdbcTemplate;
  private final String SCHEMA;
  private final String PACKAGE;

  private final SimpleJdbcCall updateUsuarioCall;

  @Autowired
  UsuarioRepositoryImpl(
//          @Qualifier(DataBaseUtil.HIKARI_DATA_SOURCE) DataSource dataSource,
          @Qualifier(DataBaseUtil.HIKARI_CONNECTION) JdbcTemplate jdbcTemplate,
          @Value("${oracle.usuario.schema}") String usuarioSchema,
          @Value("${oracle.usuario.package}") String usuarioPackage
  ) {
//    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.jdbcTemplate = jdbcTemplate;
    this.SCHEMA = usuarioSchema;
    this.PACKAGE = usuarioPackage;

    updateUsuarioCall = new SimpleJdbcCall(jdbcTemplate);
    prepareUpdate(updateUsuarioCall);
  }

  // GetAll

  @Override
  public List<UsuarioDto> getAll() {
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_GET_ALL, 3);
    try {
      return jdbcTemplate.execute(QUERY, (CallableStatementCallback<List<UsuarioDto>>) callableStatement -> {
        callableStatement.registerOutParameter(P_O_CODIGO, OracleTypes.NUMERIC);
        callableStatement.registerOutParameter(P_O_MENSAJE, OracleTypes.VARCHAR);
        callableStatement.registerOutParameter("pCursor", OracleTypes.CURSOR);

        callableStatement.execute();

        List<UsuarioDto> usuarioDtoList = new LinkedList<>();
        UsuarioDto usuarioDto;

        try (ResultSet resultSet = (ResultSet) callableStatement.getObject("pCursor")) {
          while (resultSet.next()) {
            usuarioDto = UsuarioMapper.mapRow(resultSet);
            usuarioDtoList.add(usuarioDto);
          }
        }

        Integer code = callableStatement.getInt(P_O_CODIGO);
        String message = callableStatement.getString(P_O_MENSAJE);
        handleDbResponse(code, message);
        return usuarioDtoList;
      });
    }
    catch (GenericRuntimeException e) {
      LOG.info("*** Error repository getAll: ", e);
      throw new Error500(List.of("Incidencia al consultar usuarios - DAO"));
    }
  }

  // GetByFolio

  @Override
  public UsuarioDto getByFolio(String folio) {
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_GET_BY_FOLIO, 4);
    try {
      return jdbcTemplate.execute(QUERY, (CallableStatementCallback<UsuarioDto>) callableStatement -> {
        callableStatement.setString(1, folio);
        callableStatement.registerOutParameter(2, OracleTypes.NUMERIC);
        callableStatement.registerOutParameter(3, OracleTypes.VARCHAR);
        callableStatement.registerOutParameter(4, OracleTypes.CURSOR);

        callableStatement.execute();

        UsuarioDto usuarioDto = new UsuarioDto();

        try (ResultSet resultSet = (ResultSet) callableStatement.getObject(4)) {
          if (resultSet.next()) {
            usuarioDto = UsuarioMapper.mapRow(resultSet);
          }
        }

        Integer code = callableStatement.getInt(2);
        String message = callableStatement.getString(3);
        handleDbResponse(code, message);
        return usuarioDto;
      });
    }
    catch (GenericRuntimeException e) {
      LOG.info("*** Error repository getByFolio: ", e);
      throw new Error500(List.of("Incidencia al consultar usuario - DAO"));
    }
  }

  // Add

  @Override
  public void addUsuario(UsuarioDto usuarioDto) {
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_INSERT, 9);
    try {
      jdbcTemplate.execute(QUERY, (CallableStatementCallback<Void>) callableStatement -> {

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
        handleDbResponse(code, message);
        return null;
      });
    }
    catch (GenericRuntimeException e) {
      LOG.info("*** Error repository addUsuario: ", e);
      throw new Error500(List.of("Incidencia al registrar usuario - DAO"));
    }
  }

  // Update

  @Override
  public void updateUsuario(UsuarioDto usuarioDto) {
    try {
      SqlParameterSource params = prepareUpdateParams(usuarioDto);

      Map<String, Object> outParams = updateUsuarioCall.execute(params);

      Integer code = ((Number) outParams.get(P_O_CODIGO)).intValue();
      String message = outParams.get(P_O_MENSAJE).toString();
      handleDbResponse(code, message);
    }
    catch (GenericRuntimeException e) {
      LOG.info("*** Error repository updateUsuario: ", e);
      throw new Error500(List.of("Incidencia al actualizar usuario - DAO"));
    }
  }

  private SqlParameterSource prepareUpdateParams(UsuarioDto usuarioDto) {
    return new MapSqlParameterSource()
            .addValue(P_FOLIO, usuarioDto.getFolioId())
            .addValue(P_NOMBRE, usuarioDto.getNombre())
            .addValue(P_APELLIDO_PATERNO, usuarioDto.getApellidoPaterno())
            .addValue(P_APELLIDO_MATERNO, usuarioDto.getApellidoMaterno())
            .addValue(P_FECHA_NACIMIENTO, usuarioDto.getFechaNacimiento())
            .addValue(P_USERNAME, usuarioDto.getUsername())
            .addValue(P_EMAIL, usuarioDto.getEmail())
            .addValue(P_PASSWORD, usuarioDto.getPassword())
            .addValue(P_STATUS, usuarioDto.getStatus());
  }

  private void prepareUpdate(SimpleJdbcCall simpleJdbcCall) {
    simpleJdbcCall
            .withSchemaName(SCHEMA)
            .withCatalogName(PACKAGE)
            .withProcedureName(Constantes.SP_UPDATE)
            .withoutProcedureColumnMetaDataAccess() // Desactivar consulta automática de metadatos
            .declareParameters(
                    new SqlParameter(P_FOLIO, Types.VARCHAR),
                    new SqlParameter(P_NOMBRE, Types.VARCHAR),
                    new SqlParameter(P_APELLIDO_PATERNO, Types.VARCHAR),
                    new SqlParameter(P_APELLIDO_MATERNO, Types.VARCHAR),
                    new SqlParameter(P_FECHA_NACIMIENTO, Types.DATE),
                    new SqlParameter(P_USERNAME, Types.VARCHAR),
                    new SqlParameter(P_EMAIL, Types.VARCHAR),
                    new SqlParameter(P_PASSWORD, Types.VARCHAR),
                    new SqlParameter(P_STATUS, Types.VARCHAR),

                    new SqlOutParameter(P_O_CODIGO, Types.NUMERIC),
                    new SqlOutParameter(P_O_MENSAJE, Types.VARCHAR)
            );
    simpleJdbcCall.compile();
  }

  // Delete

  @Override
  public void deleteUsuario(String folio) {
    final String QUERY = DataBaseUtil.generateSpCall(SCHEMA, PACKAGE, Constantes.SP_DELETE, 3);

    jdbcTemplate.execute((Connection cnn) -> {
//      try (OracleCallableStatement callableStatement = cnn.prepareCall(QUERY).unwrap(OracleCallableStatement.class)) {
      try (CallableStatement callableStatement = cnn.prepareCall(QUERY)) {
        callableStatement.setString(1, folio);
        callableStatement.registerOutParameter(2, OracleTypes.NUMERIC); // Codigo
        callableStatement.registerOutParameter(3, OracleTypes.VARCHAR); // Message

        callableStatement.execute();

        Integer code = callableStatement.getInt(2);
        String message = callableStatement.getString(3);
        handleDbResponse(code, message);

        return null;
      }
      catch (GenericRuntimeException e) {
        LOG.info("*** Error repository deleteUsuario: ", e);
        throw new Error500(List.of("Incidencia al eliminar usuario - DAO"));
      }
    });
  }

  private void handleDbResponse(Integer code, String message) {
    LOG.info("*** BD Response: code: {}, message: {}", code, message);
  }

}
