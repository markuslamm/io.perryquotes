package io.perryquotes.api.exception

import jakarta.validation.ConstraintViolation

data class ValidationError(
    val fieldName: String,
    val code: String,
    val msg: String,
) {
    companion object {
        fun of(violation: ConstraintViolation<*>): ValidationError {
            return ValidationError(
                violation.propertyPath.toString(),
                violation.messageTemplate,
                violation.message
            )
        }
    }
}
