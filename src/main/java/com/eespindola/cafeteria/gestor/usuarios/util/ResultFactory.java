package com.eespindola.cafeteria.gestor.usuarios.util;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;

import java.util.List;

public class ResultFactory {

  public static final String SUCCESS_MSG = "Operacion exitosa.";
  public static final String ERROR_MSG = "Incidencia en la operacion.";

  private static final Result<Void> DEFAULT_SUCCESS = Result.<Void>builder()
          .success(true)
          .message(SUCCESS_MSG)
          .build();

  private static <T> Result.ResultBuilder<T> prepare(Boolean isSuccess, String message) {
    return Result.<T>builder()
            .success(isSuccess)
            .message(message);
  }

  public static Result<Void> success() {
    return DEFAULT_SUCCESS;
  }

  public static Result<Void> success(String message) {
    return ResultFactory.<Void>prepare(true, message).build();
  }

  public static <T> Result<T> success(String message, T data) {
    return ResultFactory.<T>prepare(true, message).data(data).build();
  }

  public static <T> Result<T> success(String message, List<T> dataList) {
    return ResultFactory.<T>prepare(true, message).dataList(dataList).build();
  }

  public static <T> Result<T> error(String message) {
    return ResultFactory.<T>prepare(false, message).build();
  }

  public static Result<String> error(String message, Integer errorCode, List<String> errorDescription) {
    return ResultFactory.<String>prepare(false, message).errorCode(errorCode).errorDescription(errorDescription)
            .build();
  }

  private ResultFactory() {
    throw new IllegalArgumentException("Util class");
  }

}
