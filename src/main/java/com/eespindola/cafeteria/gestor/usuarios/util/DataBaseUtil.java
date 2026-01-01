package com.eespindola.cafeteria.gestor.usuarios.util;

public class DataBaseUtil {

  public static final String JDBC_DATA_SOURCE = "jdbcDataSource";
  public static final String JDBC_CONNECTION = "jdbcTemplate";

  public static final String HIKARI_DATA_SOURCE = "hikariDataSource";
  public static final String HIKARI_CONNECTION = "hikariTemplate";

  public static String generateSpCall(String dbSchema, String dbPackage, String spName, int paramsNumber) {
    StringBuilder stringBuilder = new StringBuilder();
    while (paramsNumber > 0){
      stringBuilder.append("?");
      stringBuilder.append((paramsNumber > 1)? ", " : "");
      paramsNumber--;
    }
    return String.format("{CALL %s.%s.%s(%s)}", dbSchema, dbPackage, spName, stringBuilder);
  }

  private DataBaseUtil() {
    throw new IllegalArgumentException("Util class");
  }

}
