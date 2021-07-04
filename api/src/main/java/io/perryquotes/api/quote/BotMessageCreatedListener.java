package io.perryquotes.api.quote;

import io.perryquotes.api.events.BotMessageCreatedEvent;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BotMessageCreatedListener {

  private final Logger log;

  public BotMessageCreatedListener(final Logger log) {
    this.log = log;
  }

  @EventListener
  public void handMessageCreated(final BotMessageCreatedEvent event) {
    log.debug("BotMessageCreatedEvent" + event);
    //do Quote, Author, etc parsing and persisting via QuoteService
  }
}
