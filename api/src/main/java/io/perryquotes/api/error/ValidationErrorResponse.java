package io.perryquotes.api.error;

import java.util.List;

public record ValidationErrorResponse(List<ValidationError> validationErrors) {

  public record ValidationError(String fieldName, String message) { }
}
