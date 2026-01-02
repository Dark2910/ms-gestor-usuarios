package com.eespindola.cafeteria.gestor.usuarios.telegram.bot.service;

import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.event.UsuarioEvent;

public interface TelegramService {

  void sendMessage(UsuarioEvent event);

}
