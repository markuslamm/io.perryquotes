package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.events.BotMessageCreatedEvent;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuoteService extends BaseEntityService<Quote> {

  private final ApplicationEventPublisher publisher;

  public QuoteService(final Logger log, final ApplicationEventPublisher publisher) {
    super(log);
    this.publisher = publisher;
  }

  Quote handleBotMessage(final BotMessageCreatedEvent event) {
    log.debug("Handle BotMessageCreatedEvent: {}", event);
    var result = createQuoteFromBotMessage(
      event.getRawBotMessage().getUuid(),
      event.getRawBotMessage().getText());
    log.debug("Created Quote from BotMessageCreatedEvent: {}", result);
    return result;
  }

  private Quote createQuoteFromBotMessage(final UUID botMessageUuid, final String messageText) {
    //do Quote, Author, etc parsing and persisting
    return null;
  }

  @Override
  protected JpaRepository<Quote, UUID> getRepository() {
    return null;
  }
}
