package com.eespindola.cafeteria.gestor.usuarios.exception.impl;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;

import java.util.List;

public class ErrorValidation extends GenericRuntimeException {
	private static final long serialVersionUID = 1L;

	public ErrorValidation(List<String> description, ErrorEnum errorEnum) {
    super(description, errorEnum);
  }

}
