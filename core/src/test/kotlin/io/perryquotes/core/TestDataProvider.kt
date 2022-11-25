package io.perryquotes.core

import io.perryquotes.core.jooq.tables.Author.Companion.AUTHOR
import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import org.jooq.DSLContext
import java.time.LocalDateTime

class TestDataProvider private constructor() {

    companion object {

        private fun createAndGetAuthor(dslContext: DSLContext, index: Int): AuthorEntity {
            return dslContext.insertInto(AUTHOR)
                .set(AUTHOR.NAME, "Author_$index")
                .set(AUTHOR.CREATED_DATE, LocalDateTime.now())
                .set(AUTHOR.LAST_MODIFIED_DATE, LocalDateTime.now())
                .returning()
                .fetchOneInto(AuthorEntity::class.java) ?: throw IllegalArgumentException("Unable tor create Author.")
        }

        fun createAndGetAuthorList(dslContext: DSLContext, numAuthors: Int = 5): List<AuthorEntity> {
            return IntRange(1, numAuthors).map { createAndGetAuthor(dslContext, it) }
        }
    }


}