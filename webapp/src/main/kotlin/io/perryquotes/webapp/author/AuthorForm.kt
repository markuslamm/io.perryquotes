package io.perryquotes.webapp.author

import java.time.LocalDateTime
import java.util.UUID

data class AuthorForm(val name: String? = null,
                      val uuid: UUID? = null,
                      val createdDate: LocalDateTime? = null,
                      val lastModifiedDate: LocalDateTime? = null)
