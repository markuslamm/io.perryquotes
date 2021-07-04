package io.perryquotes.api.telegram;

import io.perryquotes.api.events.BotMessageProcessedEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BotMessageProcessedListener {

  private final Logger log;

  BotMessageProcessedListener(final Logger log) {
    this.log = log;
  }

  @EventListener
  public void handMessageProcessed(final BotMessageProcessedEvent event) {
    log.debug("BotMessageProcessedEvent" + event);
    //give feedback in telegram channel via BotMessageService
  }
}
