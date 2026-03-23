package com.eespindola.cafeteria.gestor.usuarios.dao.repository.impl;

import com.eespindola.cafeteria.gestor.usuarios.dao.repository.UsuarioRepository;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.Error500;
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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
        callableStatement.registerOutParameter("pCodigo", OracleTypes.NUMERIC);
        callableStatement.registerOutParameter("pMensaje", OracleTypes.VARCHAR);
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

        Integer code = callableStatement.getInt("pCodigo");
        String message = callableStatement.getString("pMensaje");
        handleDbResponse(code, message);
        return usuarioDtoList;
      });
    }
    catch (Throwable e) {
      LOG.info("*** Error repository getAll: ", e);
      throw new Error500(List.of("Error al consultar usuarios."));
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
    catch (Throwable e) {
      LOG.info("*** Error repository getByFolio: ", e);
      throw new Error500(List.of("Error al consultar usuario."));
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
    catch (Throwable e) {
      LOG.info("*** Error repository addUsuario: ", e);
      throw new Error500(List.of("Error al insertar en DB"));
    }
  }

  // Update

  @Override
  public void updateUsuario(UsuarioDto usuarioDto) {
    try {
      SqlParameterSource params = prepareUpdateParams(usuarioDto);

      Map<String, Object> outParams = updateUsuarioCall.execute(params);

      Integer code = ((Number) outParams.get("pCodigo")).intValue();
      String message = outParams.get("pMensaje").toString();
      handleDbResponse(code, message);
    }
    catch (Throwable e) {
      LOG.info("*** Error repository updateUsuario: ", e);
      throw new Error500(List.of("Error al insertar en DB"));
    }
  }

  private SqlParameterSource prepareUpdateParams(UsuarioDto usuarioDto) {
    return new MapSqlParameterSource()
            .addValue("pFolio", usuarioDto.getFolioId())
            .addValue("pNombre", usuarioDto.getNombre())
            .addValue("pApellidoPaterno", usuarioDto.getApellidoPaterno())
            .addValue("pApellidoMaterno", usuarioDto.getApellidoMaterno())
            .addValue("pFechaNacimiento", usuarioDto.getFechaNacimiento())
            .addValue("pUsername", usuarioDto.getUsername())
            .addValue("pEmail", usuarioDto.getEmail())
            .addValue("pPassword", usuarioDto.getPassword())
            .addValue("pStatus", usuarioDto.getStatus());
  }

  private void prepareUpdate(SimpleJdbcCall simpleJdbcCall) {
    simpleJdbcCall
            .withSchemaName(SCHEMA)
            .withCatalogName(PACKAGE)
            .withProcedureName(Constantes.SP_UPDATE)
            .withoutProcedureColumnMetaDataAccess() // Desactivar consulta automática de metadatos
            .declareParameters(
                    new SqlParameter("pFolio", Types.VARCHAR),
                    new SqlParameter("pNombre", Types.VARCHAR),
                    new SqlParameter("pApellidoPaterno", Types.VARCHAR),
                    new SqlParameter("pApellidoMaterno", Types.VARCHAR),
                    new SqlParameter("pFechaNacimiento", Types.DATE),
                    new SqlParameter("pUsername", Types.VARCHAR),
                    new SqlParameter("pEmail", Types.VARCHAR),
                    new SqlParameter("pPassword", Types.VARCHAR),
                    new SqlParameter("pStatus", Types.VARCHAR),

                    new SqlOutParameter("pCodigo", Types.NUMERIC),
                    new SqlOutParameter("pMensaje", Types.VARCHAR)
            );
    simpleJdbcCall.compile();
  }

  // Delete

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
        handleDbResponse(code, message);

        return code;
      }
      catch (Throwable e) {
        LOG.info("*** Error repository deleteUsuario: ", e);
        throw new Error500(List.of("Error al eliminar usuario en DB"));
      }
    });
  }

  private void handleDbResponse(Integer code, String message) {
    LOG.info("*** BD Response: code: {}, message: {}", code, message);
  }

}
