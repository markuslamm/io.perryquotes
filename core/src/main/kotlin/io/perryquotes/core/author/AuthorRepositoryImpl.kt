package io.perryquotes.core.author

import io.perryquotes.core.jooq.Tables
import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AuthorRepositoryImpl(private val jooq: DSLContext) : AuthorRepository {

    override fun findByUuid(uuid: UUID): AuthorEntity? {
        return jooq
                .selectFrom(Tables.AUTHOR)
                .where(Tables.AUTHOR.UUID.eq(uuid))
                .fetchOneInto(AuthorEntity::class.java)
    }

    override fun findByName(name: String): AuthorEntity? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<AuthorEntity> {
        TODO("Not yet implemented")
    }

    override fun create(author: AuthorEntity): AuthorEntity {
        TODO("Not yet implemented")
    }

    override fun update(author: AuthorEntity): AuthorEntity {
        TODO("Not yet implemented")
    }

    override fun delete(uuid: UUID): Int {
        TODO("Not yet implemented")
    }
}
