package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.ErrorData;

import java.util.List;

public class GenericException extends RuntimeException implements ErrorData {

  private final List<String> description;
  private final int errorCode;

  GenericException(List<String> description, int errorCode) {
    super();
    this.description = description;
    this.errorCode = errorCode;
  }

  @Override
  public List<String> getDescription() {
    return description;
  }

  @Override
  public int getErrorCode() {
    return errorCode;
  }
}
