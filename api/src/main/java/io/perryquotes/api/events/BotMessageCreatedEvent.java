package io.perryquotes.api.events;

import io.perryquotes.api.telegram.BotMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

public class BotMessageCreatedEvent extends ApplicationEvent {

  private final BotMessage botMessage;

  public BotMessageCreatedEvent(final Object source, final BotMessage botMessage) {
    super(source);
    this.botMessage = botMessage;
  }

  public BotMessage getRawBotMessage() {
    return botMessage;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("rawBotMessage", botMessage)
      .build();
  }
}
