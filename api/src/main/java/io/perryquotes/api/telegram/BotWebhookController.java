package io.perryquotes.api.telegram;

import io.perryquotes.api.base.LoggableComponent;
import io.perryquotes.api.processor.MessageProcessor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BotWebhookController extends LoggableComponent {

  private final MessageProcessor messageProcessor;

  public BotWebhookController(final Logger log, final MessageProcessor messageProcessor) {
    super(log);
    this.messageProcessor = messageProcessor;
  }

  @PostMapping(value = "/webhook")
  public ResponseEntity<Void> telegramWebhook(@Valid @RequestBody IncomingBotMessage message) {
    log.info("Received telegram update: " + message);
    var created = messageProcessor.processMessage(message);
    log.info("Created BotMessage: {}", created);

    return ResponseEntity.noContent().build();
  }
}
