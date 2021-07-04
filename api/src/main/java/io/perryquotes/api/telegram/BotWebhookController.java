package io.perryquotes.api.telegram;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BotWebhookController {

  private final Logger log;
  private final BotMessageService service;

  public BotWebhookController(final Logger log, final BotMessageService service) {
    this.log = log;
    this.service = service;
  }

  @PostMapping(value = "/webhook")
  public ResponseEntity<Void> telegramWebhook(@Valid @RequestBody IncomingBotMessage message) {
    log.info("Received bot message update: " + message);
    var created = service.storeMessage(message);
    log.info("RawBotMessage created: " + created);
    return ResponseEntity.noContent().build();
  }
}
