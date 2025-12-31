package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class Error400 extends GenericException {
  // Bad Request
  public Error400(List<String> description) {
    super(description, ErrorEnum.ERROR_400.getErrorCode());
  }
}
