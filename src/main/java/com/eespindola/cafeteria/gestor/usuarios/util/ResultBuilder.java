package com.eespindola.cafeteria.gestor.usuarios.util;

import com.eespindola.cafeteria.gestor.usuarios.model.Result;

import java.util.List;

public class ResultBuilder {

  public static <T> Result<T> buildSuccess(String message, T data) {
    return Result.<T>builder()
            .success(true)
            .message(message)
            .data(data)
            .build();
  }

  public static <T> Result<T> buildSuccess(String message, List<T> dataList) {
    return Result.<T>builder()
            .success(true)
            .message(message)
            .dataList(dataList)
            .build();
  }

  public static <T> Result<T> buildError(String message, Integer errorCode, List<String> errorDescription) {
    return Result.<T>builder()
            .success(false)
            .message(message)
            .errorCode(errorCode)
            .errorDescription(errorDescription)
            .build();
  }

  private ResultBuilder() {
    throw new IllegalArgumentException("Util class");
  }

  public static class ResultConstants {

    public static final String SUCCESS = "Operacion exitosa";
    public static final String ERROR = "Error en operacion";

    private ResultConstants(){
      throw new IllegalArgumentException("Util class");
    }
  }
}
