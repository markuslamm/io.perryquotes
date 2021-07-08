package io.perryquotes.api.quote;

import io.perryquotes.api.events.BotMessageCreatedEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BotMessageCreatedListener {

  private final Logger log;
  private final QuoteService quoteService;

  public BotMessageCreatedListener(final Logger log, final QuoteService quoteService) {
    this.log = log;
    this.quoteService = quoteService;
  }

  @EventListener
  public void handMessageCreated(final BotMessageCreatedEvent event) {
    log.info("Handle BotMessageCreatedEvent" + event);
    var result = quoteService.handleBotMessage(event);
    log.info("Created Quote from BotMessageCreatedEvent: {}", result);
    
  }
}
