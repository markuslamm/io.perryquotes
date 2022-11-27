package io.perryquotes.api.exception

data class ValidationErrorResponse(val errors: List<ValidationError>)
