package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class Error500 extends GenericRuntimeException {
  // Internal Server Error
  public Error500(List<String> description) {
    super(description, ErrorEnum.ERROR_500);
  }

  public Error500(List<String> description, Throwable e) {
    super(description, ErrorEnum.ERROR_500, e);
  }

}
