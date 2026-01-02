package com.eespindola.cafeteria.gestor.usuarios.telegram.bot.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.event.UsuarioEvent;
import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.service.TelegramService;
import com.eespindola.cafeteria.gestor.usuarios.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:telegram.properties")
public class TelegramServiceImpl implements TelegramService {

  private final WebClient webClient;
  private final String BASEPATH;

  @Autowired
  TelegramServiceImpl(
          @Value("${telegram.bot.basePath}") String telegramBotBasePath
  ) {
    this.webClient = WebClient.builder().build();
    this.BASEPATH = telegramBotBasePath;
  }

  @Override
  public void sendMessage(UsuarioEvent event) {

    String telegramUri = BASEPATH + Constantes.ENDPOINT_SEND_MESSAGE;

    Map<String, String> telegramRequest = new HashMap<>();
    telegramRequest.put("message", event.getMessage());

    webClient.post()
            .uri(telegramUri)
            .bodyValue(telegramRequest)
            .retrieve()
            .bodyToMono(String.class)
            .subscribe(); // peticion asincrona
//            .block(); // Peticion sincrona
  }
}
