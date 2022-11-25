package io.perryquotes.core.author

import io.perryquotes.core.TestCoreConfig
import io.perryquotes.core.TestDataProvider
import io.perryquotes.core.jooq.tables.Author.Companion.AUTHOR
import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import io.perryquotes.testutils.WithPostgresContainer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ContextConfiguration
import java.util.*

@JooqTest
@ContextConfiguration(initializers = [WithPostgresContainer::class], classes = [TestCoreConfig::class])
class AuthorRepositoryIT {

    @Autowired
    private lateinit var testee: AuthorRepository

    @Autowired
    private lateinit var dslContext: DSLContext

    @Test
    fun `should return all Author entities`() {
        val authors = TestDataProvider.createAndGetAuthorList(dslContext)
        assertThat(testee.findAll()).hasSize(authors.size)
    }

    @Test
    fun `find by existing uuid should return correct Author from a given entity list`() {
        TestDataProvider.createAndGetAuthorList(dslContext).forEach { author ->
            testee.findByUuid(author.uuid!!)?.let {
                assertThat(it).isNotNull
                assertThat(it).isEqualTo(author)
            }
        }
    }

    @Test
    fun `find by unknown UUID should be null`() {
        assertThat(testee.findByUuid(UUID.randomUUID())).isNull()
    }

    @Test
    fun `find by existing name should return correct Author from a given entity list`() {
        TestDataProvider.createAndGetAuthorList(dslContext).forEach { author ->
            testee.findByName(author.name!!)?.let {
                assertThat(it).isNotNull
                assertThat(it).isEqualTo(author)
            }
        }
    }

    @Test
    fun `find by unknown name should be null`() {
        assertThat(testee.findByName("Unknown")).isNull()
    }

    @Test
    fun `should create valid Author entity`() {
        val author = AuthorEntity(name = "Author1")
        val created = testee.create(author)

        with(created) {
            assertThat(this).isNotNull
            assertThat(uuid).isNotNull
            assertThat(name).isEqualTo(author.name)
            assertThat(createdDate).isNotNull
            assertThat(lastModifiedDate).isNotNull
            assertThat(createdDate).isEqualTo(lastModifiedDate)
        }

        assertThat(dslContext
            .selectFrom(AUTHOR)
            .where(AUTHOR.UUID.eq(created.uuid))
            .fetchOneInto(AuthorEntity::class.java)
        ).isEqualTo(created)
    }

    @Test
    fun `create Author with existing name should throw DataIntegrityViolationException`() {
        val existing = TestDataProvider.createAndGetAuthorList(dslContext).last()
        val author = AuthorEntity(name = existing.name)
        assertThatThrownBy { testee.create(author) }.isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun `should update Author entity`() {
        val existing = TestDataProvider.createAndGetAuthorList(dslContext).last()
        val author = AuthorEntity(uuid = existing.uuid, name = "New Name")

        val update = testee.update(author)

        with(update) {
            assertThat(this).isNotNull
            assertThat(uuid).isNotNull
            assertThat(uuid).isEqualTo(existing.uuid)
            assertThat(name).isNotNull
            assertThat(name).isEqualTo(author.name)
            assertThat(createdDate).isBefore(lastModifiedDate)
        }
    }

    @Test
    fun `update Author with existing name should throw DataIntegrityViolationException`() {

        val existing = TestDataProvider.createAndGetAuthorList(dslContext)
        val toUpdate = existing.last()
        val conflictingName = existing.first().name
        val authorRequest = AuthorEntity(uuid = toUpdate.uuid, name = conflictingName)

        assertThatThrownBy { testee.update(authorRequest) }
            .isInstanceOf(DataIntegrityViolationException::class.java)
    }

    @Test
    fun `should delete Author entity`() {
        val existing = TestDataProvider.createAndGetAuthorList(dslContext)
        val toDelete = existing.last()

        testee.delete(toDelete.uuid!!)

        assertThat(
            dslContext
                .selectFrom(AUTHOR)
                .where(AUTHOR.UUID.eq(toDelete.uuid))
                .execute()
        ).isEqualTo(0)
    }

}