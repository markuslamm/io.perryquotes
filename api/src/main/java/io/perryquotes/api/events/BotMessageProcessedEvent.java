package io.perryquotes.api.events;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

public class BotMessageProcessedEvent extends ApplicationEvent {

  public BotMessageProcessedEvent(final Object source) {
    super(source);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .build();
  }
}
