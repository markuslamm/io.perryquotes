package io.perryquotes.core.author

import io.perryquotes.core.jooq.tables.pojos.AuthorEntity
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


}
