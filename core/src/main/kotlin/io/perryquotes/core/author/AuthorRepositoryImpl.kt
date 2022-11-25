package io.perryquotes.core.author

import io.perryquotes.core.exception.CoreException
import io.perryquotes.core.jooq.tables.Author.Companion.AUTHOR
import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class AuthorRepositoryImpl(private val jooq: DSLContext) : AuthorRepository {

    override fun findByUuid(uuid: UUID): AuthorEntity? {
        return jooq
            .selectFrom(AUTHOR)
            .where(AUTHOR.UUID.eq(uuid))
            .fetchOneInto(AuthorEntity::class.java)
    }

    override fun findByName(name: String): AuthorEntity? {
        return jooq
            .selectFrom(AUTHOR)
            .where(AUTHOR.NAME.eq(name))
            .fetchOneInto(AuthorEntity::class.java)
    }

    override fun findAll(): List<AuthorEntity> {
        return jooq
            .selectFrom(AUTHOR)
            .fetchInto(AuthorEntity::class.java)
    }

    override fun create(author: AuthorEntity): AuthorEntity {
        return jooq
            .insertInto(AUTHOR)
            .set(AUTHOR.NAME, author.name)
            .returning()
            .fetchOneInto(AuthorEntity::class.java) ?: throw CoreException("Create Author results in NULL: $author")
    }

    override fun update(author: AuthorEntity): AuthorEntity {
        return jooq
            .update(AUTHOR)
            .set(AUTHOR.NAME, author.name)
            .set(AUTHOR.LAST_MODIFIED_DATE, LocalDateTime.now())
            .where(AUTHOR.UUID.eq(author.uuid))
            .returning()
            .fetchOneInto(AuthorEntity::class.java) ?: throw CoreException("Update Author results in NULL: $author.")
    }

    override fun delete(uuid: UUID): Int {
        return jooq
            .deleteFrom(AUTHOR)
            .where(AUTHOR.UUID.eq(uuid))
            .execute()    }
}
