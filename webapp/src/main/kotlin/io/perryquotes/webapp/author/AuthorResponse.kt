package io.perryquotes.webapp.author

import io.perryquotes.webapp.base.ApiResponse
import java.time.LocalDateTime
import java.util.*

data class AuthorResponse(val uuid: UUID,
                          val name: String,
                          val createdDate: LocalDateTime,
                          val lastModifiedDate: LocalDateTime): ApiResponse
