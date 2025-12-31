package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class Error403 extends GenericException {
  // Forbidden
  public Error403(List<String> description) {
    super(description, ErrorEnum.ERROR_403.getErrorCode());
  }
}
