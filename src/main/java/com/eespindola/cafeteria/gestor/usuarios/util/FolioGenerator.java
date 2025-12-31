package com.eespindola.cafeteria.gestor.usuarios.util;

import java.time.LocalDateTime;

import java.util.Random;
import java.util.stream.Collectors;

public class FolioGenerator {

  private static final String FOLIO_FORMAT = "yyyyMMddHHmmss";
  private static final Random RANDOM = new Random();
  private static final int RANDOM_DIGITS = 16;

  public static String getFolio() {

    String randomNumber = RANDOM.ints(RANDOM_DIGITS, 0, 10)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());

    String date = FechasUtil.toString(FOLIO_FORMAT, LocalDateTime.now());

    return (randomNumber + date);
  }

  private FolioGenerator() {
    throw new IllegalArgumentException("Util class");
  }

}
