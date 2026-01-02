package com.eespindola.cafeteria.gestor.usuarios.telegram.bot.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEvent {
  private String errorMessage;
  private List<String> errorDescription;

  public String getMessage(){
    String detalles = String.join("\n", errorDescription);
    return errorMessage + "\n" + detalles;
  }
}
