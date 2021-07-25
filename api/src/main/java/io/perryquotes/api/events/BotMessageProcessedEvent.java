package io.perryquotes.api.events;

import io.perryquotes.api.quote.Quote;
import io.perryquotes.api.telegram.BotMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

public class BotMessageProcessedEvent extends ApplicationEvent {

  private final BotMessage botMessage;
  private final Quote quoteResult;


  public BotMessageProcessedEvent(final Object source, final BotMessage botMessage, final Quote quoteResult) {
    super(source);
    this.botMessage = botMessage;
    this.quoteResult = quoteResult;
  }

  public BotMessage getBotMessage() {
    return botMessage;
  }

  public Quote getQuoteResult() {
    return quoteResult;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("botMessage", botMessage)
      .append("quoteResult", quoteResult)
      .build();
  }
}
