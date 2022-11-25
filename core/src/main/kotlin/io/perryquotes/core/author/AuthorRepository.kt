package io.perryquotes.core.author

import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import java.util.*

interface AuthorRepository {

    fun findByUuid(uuid: UUID): AuthorEntity?
    fun findByName(name: String): AuthorEntity?
    fun findAll(): List<AuthorEntity>
    fun create(author: AuthorEntity): AuthorEntity
    fun update(author: AuthorEntity): AuthorEntity
    fun delete(uuid: UUID): Int
}
