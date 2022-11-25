package io.perryquotes.core.author

import io.perryquotes.core.exception.EntityNotFoundException

data class AuthorNotFoundException(val msg: String): EntityNotFoundException(msg)
