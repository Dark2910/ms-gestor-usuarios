package com.eespindola.cafeteria.gestor.usuarios.exception.controller;

import com.eespindola.cafeteria.gestor.usuarios.exception.enums.ErrorEnum;
import com.eespindola.cafeteria.gestor.usuarios.exception.impl.*;
import com.eespindola.cafeteria.gestor.usuarios.model.Result;
import com.eespindola.cafeteria.gestor.usuarios.util.ResultBuilder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(GenericException.class)
  public ResponseEntity<Result<String>> genericExceptionController(GenericException exception) {
    ErrorEnum errorEnum = ErrorEnum.getEnum(exception.getErrorCode());
    return ResponseEntity
            .status(errorEnum.getStatus())
            .body(ResultBuilder.buildError(errorEnum.getMessage(), exception.getErrorCode(), exception.getDescription()));
  }

  // Validation
  @ExceptionHandler(MethodArgumentNotValidException.class)
  private ResponseEntity<Result<String>> errorRequestController(MethodArgumentNotValidException e) {

    List<String> errorList = e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    return ResponseEntity
            .status(ErrorEnum.ERROR_400.getStatus())
            .body(ResultBuilder.buildError(ErrorEnum.ERROR_400.getMessage(), ErrorEnum.ERROR_400.getErrorCode(), errorList));
  }

}
