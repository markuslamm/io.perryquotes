package io.perryquotes.api.telegram;

import io.perryquotes.api.events.BotMessageProcessedEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BotMessageProcessedListener {

  private final Logger log;
  private final BotMessageService botMessageService;
  private final TelegramResponseClient telegramResponseClient;

  BotMessageProcessedListener(final Logger log, final BotMessageService botMessageService,
                              final TelegramResponseClient telegramResponseClient) {
    this.log = log;
    this.botMessageService = botMessageService;
    this.telegramResponseClient = telegramResponseClient;
  }

  @EventListener
  public void handMessageProcessed(final BotMessageProcessedEvent event) {
    log.debug("Received BotMessageProcessedEvent" + event);
    var processedMessage = botMessageService.setProcessingStatus(
      event.getBotMessageUuid(),
      (event.getQuote().isPresent()) ? ProcessingStatus.SUCCESS : ProcessingStatus.FAILURE);
    log.info("Set BotMessage processing state: {}", processedMessage.getProcessingStatus());
    var responseStatus = telegramResponseClient.sendTelegramResponse(event.getBotMessageUuid(), event.getQuote());
    log.info("Send Telegram response: {}", responseStatus);
  }
}
