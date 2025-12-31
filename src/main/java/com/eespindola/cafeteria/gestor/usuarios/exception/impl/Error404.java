package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class Error404 extends GenericException {
  // Not Found
  public Error404(List<String> description) {
    super(description, ErrorEnum.ERROR_404.getErrorCode());
  }
}
