package com.eespindola.cafeteria.gestor.usuarios.telegram.bot.event;

import lombok.Builder;

import java.util.List;

@Builder
public class UsuarioEvent {
  private String errorMessage;
  private List<String> errorDescription;

  public String getMessage() {
    String detalles = String.join("\n", errorDescription);
    return errorMessage + "\n" + detalles;
  }

  public UsuarioEvent() {
    super();
  }

  public UsuarioEvent(String errorMessage, List<String> errorDescription) {
    super();
    this.errorMessage = errorMessage;
    this.errorDescription = errorDescription;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public List<String> getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(List<String> errorDescription) {
    this.errorDescription = errorDescription;
  }

}
