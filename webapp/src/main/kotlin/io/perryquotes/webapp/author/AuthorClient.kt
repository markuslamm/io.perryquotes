package io.perryquotes.webapp.author

import java.time.LocalDateTime
import java.util.*

interface AuthorClient {

    data class Author(
        val uuid: UUID,
        val name: String,
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val lastModifiedDate: LocalDateTime = LocalDateTime.now()
    )

    fun getAllAuthors(): List<Author>
    fun getAuthorByUuid(uuid: UUID): Author?

    fun createAuthor(authorForm: AuthorForm): Author
    fun updateAuthor(uuid: UUID, authorForm: AuthorForm): Author
}
