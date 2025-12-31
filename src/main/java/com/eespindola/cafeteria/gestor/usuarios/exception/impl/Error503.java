package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class Error503 extends GenericException {
  // Service Unavailable
  public Error503(List<String> description) {
    super(description, ErrorEnum.ERROR_503.getErrorCode());
  }
}
