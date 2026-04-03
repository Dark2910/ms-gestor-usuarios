package com.eespindola.cafeteria.gestor.usuarios.util;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;

import java.util.List;

public class ResultFactory {

  private static final String SUCCESS_MSG = "Operacion exitosa.";

  private static <T> Result.ResultBuilder<T> prepare(Boolean isSuccess, String message) {
    return Result.<T>builder()
            .success(isSuccess)
            .message(message);
  }

  public static Result<Void> success() {
    return ResultFactory.<Void>prepare(true, SUCCESS_MSG).build();
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

  public static Result<String> error(String message, Integer errorCode, List<String> errorDescription) {
    return ResultFactory.<String>prepare(false, message).errorCode(errorCode).errorDescription(errorDescription)
            .build();
  }

  private ResultFactory() {
    throw new IllegalArgumentException("Util class");
  }

}
