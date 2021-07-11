package io.perryquotes.api.error;

public class InvalidDataException extends RuntimeException {

  public InvalidDataException(String msg) {
    super(msg);
  }
}
