package com.eespindola.cafeteria.gestor.usuarios.util;

public class DataBaseUtil {

  public static final String JDBC_DATA_SOURCE = "jdbcDataSource";
  public static final String JDBC_CONNECTION = "jdbcTemplate";

  public static final String HIKARI_DATA_SOURCE = "hikariDataSource";
  public static final String HIKARI_CONNECTION = "hikariTemplate";

  private DataBaseUtil() {
    throw new IllegalArgumentException("Util class");
  }

}
