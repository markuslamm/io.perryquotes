package io.perryquotes.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

  //TODO
  //EntityNotFoundException, InvalidDataException

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onConstraintValidationException(final ConstraintViolationException ex) {
    var errors = ex.getConstraintViolations()
      .stream()
      .map(violation -> new ValidationErrorResponse.ValidationError(
        violation.getPropertyPath().toString(), violation.getMessage()))
      .collect(Collectors.toList());

    return new ValidationErrorResponse(errors);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
    var errors = ex.getBindingResult().getFieldErrors()
      .stream()
      .map(fieldError -> new ValidationErrorResponse.ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
      .collect(Collectors.toList());

    return new ValidationErrorResponse(errors);
  }
}
