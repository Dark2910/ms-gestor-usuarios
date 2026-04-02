package com.eespindola.cafeteria.gestor.usuarios.exception;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public interface ErrorData {

  List<String> getDescription();
  ErrorEnum getErrorEnum();

}
