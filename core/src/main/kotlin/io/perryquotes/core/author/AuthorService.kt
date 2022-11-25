package io.perryquotes.core.author

import io.perryquotes.core.exception.InvalidDataException
import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
class AuthorService(private val repository: AuthorRepository) {

    companion object : KLogging()

    @Transactional(readOnly = true)
    fun getByUuid(uuid: UUID): AuthorEntity? {
        logger.debug { "AuthorService.getByUuid = $uuid" }
        return repository.findByUuid(uuid)
    }

    @Transactional(readOnly = true)
    fun getAll(): List<AuthorEntity> {
        logger.debug { "AuthorService.getAll" }
        return repository.findAll()
    }

    @Transactional
    fun create(@Valid request: AuthorRequest): AuthorEntity {
        logger.debug { "AuthorService.create = $request" }
        if (repository.findByName(request.name) != null) {
            throw InvalidDataException("Author[name=${request.name}] already exists")
        }
        val created = repository.create(AuthorEntity(name = request.name))
        logger.info { "Created Author: $created" }
        return created
    }

    @Transactional
    fun update(uuid: UUID, @Valid request: AuthorRequest): AuthorEntity {
        logger.debug { "AuthorService.update = $uuid, $request" }
        if (repository.findByName(request.name) != null) {
            throw InvalidDataException("Author[name=${request.name}] already exists")
        }
        val updated = repository.findByUuid(uuid)?.let {
            repository.update(it.copy(name = request.name))
        } ?: throw authorNotFound(uuid)
        logger.info { "Updated Author[uuid=$uuid]: $updated" }
        return updated
    }

    @Transactional
    fun delete(uuid: UUID): Boolean {
        logger.debug { "AuthorService.delete = $uuid" }
        return repository.findByUuid(uuid)?.let { repository.delete(uuid) == 1
        }  ?: throw authorNotFound(uuid)
    }

    private fun authorNotFound(uuid: UUID): AuthorNotFoundException =
        AuthorNotFoundException("Author[uuid=$uuid] not found")


}
