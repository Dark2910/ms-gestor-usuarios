package com.eespindola.cafeteria.gestor.usuarios.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FechasUtil {

  public static final String FORMAT_1 = "yyyy-MM-dd";
  public static final String FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_3 = "yyyy-MM-dd HH:mm";

  public static final String FORMAT_4 = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String FORMAT_5 = "yyyy-MM-dd'T'HH:mm";

  public static final String FORMAT_6 = "yyyy/MM/dd";
  public static final String FORMAT_7 = "yyyy/MM/dd HH:mm:ss";
  public static final String FORMAT_8 = "yyyy/MM/dd HH:mm";

  public static final String FORMAT_9 = "yyyy/MM/dd'T'HH:mm:ss";
  public static final String FORMAT_10 = "yyyy/MM/dd'T'HH:mm";

  public static final String FORMAT_11 = "dd-MM-yyyy";
  public static final String FORMAT_12 = "dd-MM-yyyy HH:mm:ss";
  public static final String FORMAT_13 = "dd-MM-yyyy HH:mm";

  public static final String FORMAT_14 = "dd-MM-yyyy'T'HH:mm:ss";
  public static final String FORMAT_15 = "dd-MM-yyyy'T'HH:mm";

  public static final String FORMAT_16 = "dd/MM/yyyy";
  public static final String FORMAT_17 = "dd/MM/yyyy HH:mm:ss";
  public static final String FORMAT_18 = "dd/MM/yyyy HH:mm";

  public static final String FORMAT_19 = "dd/MM/yyyy'T'HH:mm:ss";
  public static final String FORMAT_20 = "dd/MM/yyyy'T'HH:mm";

  private static final Map<String, DateTimeFormatter> FORMAT_CACHE = new ConcurrentHashMap<>();

  private static DateTimeFormatter getFormatter(String pattern) {
    return FORMAT_CACHE.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
  }

  public static LocalDate toLocalDate(String pattern, String date) {
    return LocalDate.parse(date, getFormatter(pattern));
  }

  public static String toString(String pattern, LocalDate localDate) {
    return localDate.format(getFormatter(pattern));
  }

  public static LocalDateTime toLocalDateTime(String pattern, String date) {
    return LocalDateTime.parse(date, getFormatter(pattern));
  }

  public static String toString(String pattern, LocalDateTime localDateTime) {
    return localDateTime.format(getFormatter(pattern));
  }

  private FechasUtil() {
    throw new IllegalArgumentException("Util class");
  }


}
