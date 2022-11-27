package io.perryquotes.core.author

import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import java.time.LocalDateTime
import java.util.*

data class AuthorModel(
    val uuid: UUID,
    val name: String,
    val createdDate: LocalDateTime,
    val lastModifiedDate: LocalDateTime,
)

fun AuthorEntity.toModel() = AuthorModel(uuid!!, name!!, createdDate!!, lastModifiedDate!!)