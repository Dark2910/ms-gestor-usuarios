package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class Error401 extends GenericException {
  // Unauthorized
  public Error401(List<String> description) {
    super(description, ErrorEnum.ERROR_401.getErrorCode());
  }
}
