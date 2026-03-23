package com.eespindola.cafeteria.gestor.usuarios.telegram.bot.service.impl;

import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.event.UsuarioEvent;
import com.eespindola.cafeteria.gestor.usuarios.telegram.bot.service.TelegramService;
import com.eespindola.cafeteria.gestor.usuarios.util.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:telegram.properties")
public class TelegramServiceImpl implements TelegramService {
  private static final Logger LOG = LoggerFactory.getLogger(TelegramServiceImpl.class);

  private final WebClient webClient;
  private final String BASEPATH;

  @Autowired
  TelegramServiceImpl(@Value("${telegram.bot.basePath}") String telegramBotBasePath) {
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
            .doOnError(e -> LOG.error("*** Error al mandar mensaje: {}", e.getMessage()))
            .onErrorResume(e -> Mono.empty()) // No subir log general
            .subscribe(); // peticion asincrona
//            .block(); // Peticion sincrona
  }

}
