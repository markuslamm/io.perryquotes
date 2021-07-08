package io.perryquotes.api.error;

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
}
