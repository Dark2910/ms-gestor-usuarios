package com.eespindola.cafeteria.gestor.usuarios.telegram.bot.listener;

import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.event.UsuarioEvent;
import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.service.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioListener {

  private final TelegramService service;

  @Autowired
  UsuarioListener(
          TelegramService telegramService
  ){
    this.service = telegramService;
  }

  @EventListener
  public void telegramListener(UsuarioEvent event){
    service.sendMessage(event);
  }

}
