package io.perryquotes.api.exception

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val exception: String,
    val message: String,
    val status: String,
) {


    companion object {

        private const val UNKNOWN = "unknown"
        fun of(ex: Exception, status: HttpStatus): ErrorResponse {
            return ErrorResponse(
                ex::class.simpleName ?: UNKNOWN,
                ex.message ?: UNKNOWN,
                "${status.value()}:${status.name}"
            )
        }
    }
}
