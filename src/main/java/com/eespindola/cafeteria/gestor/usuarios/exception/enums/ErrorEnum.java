package com.eespindola.cafeteria.gestor.usuarios.exception.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {

  ERROR_400(HttpStatus.BAD_REQUEST, "Bad Request", 4000),
  ERROR_401(HttpStatus.UNAUTHORIZED, "Unauthorized", 4001),
  ERROR_403(HttpStatus.FORBIDDEN, "Forbidden", 4003),
  ERROR_404(HttpStatus.NOT_FOUND, "Not Found", 4004),
  ERROR_500(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", 5000),
  ERROR_503(HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable", 5003);

  private final HttpStatus status;
  private final String message;
  private final int errorCode;

  ErrorEnum(HttpStatus status, String message, int errorCode) {
    this.status = status;
    this.message = message;
    this.errorCode = errorCode;
  }

  public static ErrorEnum getEnum(int errorCode) {
    for (ErrorEnum errorEnum : values()) {
      if (errorEnum.errorCode == errorCode) {
        return errorEnum;
      }
    }
    throw new IllegalArgumentException("Código de error desconocido: " + errorCode);
  }
}
