package io.perryquotes.api.events;

import io.perryquotes.api.quote.Quote;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

import java.util.Optional;
import java.util.UUID;

public class BotMessageProcessedEvent extends ApplicationEvent {

  private final UUID botMessageUuid;
  private final Optional<Quote> quote;

  public BotMessageProcessedEvent(final Object source, final UUID botMessageUuid, final Optional<Quote> quote) {
    super(source);
    this.botMessageUuid = botMessageUuid;
    this.quote = quote;
  }

  public UUID getBotMessageUuid() {
    return botMessageUuid;
  }

  public Optional<Quote> getQuote() {
    return quote;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("botMessageUuid", botMessageUuid)
      .append("quote", quote)
      .build();
  }
}
