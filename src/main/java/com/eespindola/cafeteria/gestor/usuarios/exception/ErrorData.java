package com.eespindola.cafeteria.gestor.usuarios.exception;

import java.util.List;

public interface ErrorData {

  List<String> getDescription();

  int getErrorCode();
}
