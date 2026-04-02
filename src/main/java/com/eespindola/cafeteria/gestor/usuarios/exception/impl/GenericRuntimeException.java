package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.ErrorData;
import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class GenericRuntimeException extends RuntimeException implements ErrorData {

  private final List<String> description;
  private final ErrorEnum errorEnum;

  public GenericRuntimeException(List<String> description, ErrorEnum errorEnum) {
    super();
    this.description = description;
    this.errorEnum = errorEnum;
  }

  public GenericRuntimeException(List<String> description, ErrorEnum errorEnum, Throwable e) {
    super(e);
    this.description = description;
    this.errorEnum = errorEnum;
  }

  @Override
  public List<String> getDescription() {
    return this.description;
  }

  @Override
  public ErrorEnum getErrorEnum() {
    return this.errorEnum;
  }

}
