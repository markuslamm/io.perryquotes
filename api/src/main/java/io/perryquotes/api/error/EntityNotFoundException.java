package io.perryquotes.api.error;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

  private final String identifier;
  private final Class<?> clazz;

  public EntityNotFoundException(final Class<?> clazz,
                                 final String identifier,
                                 final String value) {
    super(String.format("No entity %s with identifier %s[%s] found", clazz.getName(), identifier, value));
    this.identifier = identifier;
    this.clazz = clazz;
  }

  public EntityNotFoundException(final Class<?> clazz,
                                 final String identifier,
                                 final Set<UUID> values) {
    super(String.format("No entities %s with identifier %s[%s] found", clazz.getName(), identifier, StringUtils.join(values, ",")));
    this.identifier = identifier;
    this.clazz = clazz;
  }
}
