package io.perryquotes.core.author

import io.perryquotes.core.TestCoreConfig
import io.perryquotes.core.TestDataProvider.Companion.createAndGetAuthorList
import io.perryquotes.core.exception.InvalidDataException
import io.perryquotes.core.jooq.tables.Author
import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import io.perryquotes.testutils.WithPostgresContainer
import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import java.util.UUID.randomUUID

@SpringBootTest
@ContextConfiguration(initializers = [WithPostgresContainer::class], classes = [TestCoreConfig::class])
@Transactional
internal class AuthorServiceIT {

    @Autowired
    lateinit var testee: AuthorService

    @Autowired
    private lateinit var dslContext: DSLContext

    @Test
    fun `should return all Author entities`() {
        val authors = createAndGetAuthorList(dslContext)
        assertThat(testee.getAll()).hasSize(authors.size)
    }

    @Test
    fun `find by existing uuid should return correct Author from a given entity list`() {
        createAndGetAuthorList(dslContext).forEach { author ->
            testee.getByUuid(author.uuid!!)?.let {
                assertThat(it).isNotNull
                assertThat(it).isEqualTo(author.toModel())
            }
        }
    }

    @Test
    fun `find by unknown UUID should be null`() {
        assertThat(testee.getByUuid(randomUUID())).isNull()
    }

    @Test
    fun `should create valid Author entity`() {
        val request = AuthorRequest("Author 1")
        val created = testee.create(request)

        with(created) {
            assertThat(this).isNotNull
            assertThat(uuid).isNotNull
            assertThat(name).isEqualTo(request.name)
            assertThat(createdDate).isNotNull
            assertThat(lastModifiedDate).isNotNull
            assertThat(createdDate).isEqualTo(lastModifiedDate)
        }

        assertThat(
            dslContext
                .selectFrom(Author.AUTHOR)
                .where(Author.AUTHOR.UUID.eq(created.uuid))
                .fetchOneInto(AuthorEntity::class.java)?.toModel()
        ).isEqualTo(created)
    }

    @Test
    fun `create Author with empty name should throw ConstraintViolationException`() {
        assertThatThrownBy { testee.create(AuthorRequest(name = "")) }
            .isInstanceOf(ConstraintViolationException::class.java)
    }

    @Test
    fun `create Author with existing name should throw InvalidDataException`() {
        val existing = createAndGetAuthorList(dslContext).last()
        assertThatThrownBy { testee.create(AuthorRequest(name = existing.name!!)) }
            .isInstanceOf(InvalidDataException::class.java)
    }


    @Test
    fun  `should update Author entity`() {
        val existing = createAndGetAuthorList(dslContext).last()
        val request = AuthorRequest(name = "Author X")

        val update = testee.update(existing.uuid!!, request)

        with(update) {
            assertThat(this).isNotNull
            assertThat(uuid).isNotNull
            assertThat(uuid).isEqualTo(existing.uuid)
            assertThat(name).isNotNull
            assertThat(name).isEqualTo(request.name)
            assertThat(createdDate).isBefore(lastModifiedDate)
        }
    }

    @Test
    fun `update Author with empty name should throw ConstraintViolationException`() {
        val existing = createAndGetAuthorList(dslContext).last()

        assertThatThrownBy { testee.update(existing.uuid!!, AuthorRequest(name = "")) }
            .isInstanceOf(ConstraintViolationException::class.java)
    }

    @Test
    fun `update Author with unknown uuid throws AuthorNotFoundException` () {
        assertThatThrownBy { testee.update(randomUUID(), AuthorRequest(name = "Author X")) }
            .isInstanceOf(AuthorNotFoundException::class.java)
    }

    @Test
    fun `update Author with existing name throws InvalidDataException` () {
        val existing = createAndGetAuthorList(dslContext)
        val toUpdate = existing.last()
        val conflictingName = existing.first().name!!

        assertThatThrownBy { testee.update(toUpdate.uuid!!, AuthorRequest(name = conflictingName)) }
            .isInstanceOf(InvalidDataException::class.java)
    }

    @Test
    fun `should delete Author`() {
        val toDelete = createAndGetAuthorList(dslContext).last()

        testee.delete(toDelete.uuid!!)

        assertThat(
            dslContext
                .selectFrom(Author.AUTHOR)
                .where(Author.AUTHOR.UUID.eq(toDelete.uuid))
                .execute()
        ).isEqualTo(0)
    }

    @Test
    fun `delete Author with unknown uuid throws AuthorNotFoundException` () {
        assertThatThrownBy { testee.delete(randomUUID()) }
            .isInstanceOf(AuthorNotFoundException::class.java)
    }
}