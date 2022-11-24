package io.perryquotes.webapp.author

import java.util.UUID

interface AuthorClient {

    fun getAllAuthors(): List<DummyAuthorClient.Author>
    fun getAuthorByUuid(uuid: UUID): DummyAuthorClient.Author?

    fun createAuthor(authorForm: AuthorForm): DummyAuthorClient.Author
    fun updateAuthor(uuid: UUID, authorForm: AuthorForm): DummyAuthorClient.Author
}
