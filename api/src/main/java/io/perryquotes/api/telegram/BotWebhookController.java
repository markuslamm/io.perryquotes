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

  public BotWebhookController(final Logger log) {
    this.log = log;
  }

  @PostMapping(value = "/webhook")
  public ResponseEntity<Void> telegramWebhook(@Valid @RequestBody IncomingBotMessage update) {
    log.info("Received Telegram update: " + update);
    return ResponseEntity.noContent().build();
  }
}
