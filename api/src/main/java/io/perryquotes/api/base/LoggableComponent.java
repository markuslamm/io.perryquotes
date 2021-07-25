package io.perryquotes.api.base;

import org.slf4j.Logger;

public abstract class LoggableComponent {

  protected final Logger log;

  protected LoggableComponent(final Logger log) {
    this.log = log;
  }
}
