package io.perryquotes.api.exception

import io.perryquotes.core.exception.CoreException
import io.perryquotes.core.exception.EntityNotFoundException
import io.perryquotes.core.exception.InvalidDataException
import jakarta.validation.ConstraintViolationException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHandlingControllerAdvice {

    companion object : KLogging()

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onConstraintValidationException(ex: ConstraintViolationException): ValidationErrorResponse {
        logger.info { "Handle ConstraintViolationException: $ex" }
        val errors = ex.constraintViolations.map { ValidationError.of(it) }
        return ValidationErrorResponse(errors)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun onEntityNotFoundException(ex: EntityNotFoundException): ErrorResponse {
        logger.info { "Handle EntityNotFoundException: $ex" }
        return ErrorResponse.of(ex, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidDataException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onInvalidDataException(ex: InvalidDataException): ErrorResponse {
        logger.info { "Handle InvalidDataException: $ex" }
        return ErrorResponse.of(ex, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(CoreException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun onCoreException(ex: CoreException): ErrorResponse {
        logger.info { "Handle CoreException: $ex" }
        return ErrorResponse.of(ex, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ValidationErrorResponse {
        val errors = ex.bindingResult.fieldErrors
            //TODO defaultMessage is just NotBlank for example
            .map { ValidationError(it.field, it.defaultMessage ?: "UNKNOWN", it.code ?: "UNKNOWN") }
        return ValidationErrorResponse(errors)
    }
}
