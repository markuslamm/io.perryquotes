package io.perryquotes.api.author

import io.perryquotes.core.author.AuthorNotFoundException
import io.perryquotes.core.author.AuthorRequest
import io.perryquotes.core.author.AuthorService
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@RestController
class AuthorController(private val authorService: AuthorService) {

    companion object : KLogging()

    @GetMapping("/authors/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<AuthorView> {
        logger.debug { "Handle request GET /authors/$uuid" }
        return authorService.getByUuid(uuid)?.let {
            ResponseEntity.ok(it.toView())
        } ?: throw AuthorNotFoundException("Author[uuid=$uuid] not found")
    }

    @GetMapping("/authors")
    fun getAll(): ResponseEntity<List<AuthorView>> {
        logger.debug { "Handle request GET /authors" }
        return ResponseEntity.ok(authorService.getAll().map { it.toView() })
    }

    @PostMapping("/authors")
    fun createSource(
        @Valid @RequestBody request: AuthorRequest,
        uriBuilder: UriComponentsBuilder,
    ): ResponseEntity<AuthorView> {
        logger.debug { "Handle request POST /authors, body=$request" }
        val created = authorService.create(request)
        val location = uriBuilder.path("/authors/{uuid}").buildAndExpand(created.uuid).toUri()
        logger.info { "Created Author=$created at location $location" }
        return ResponseEntity.created(location).body(created.toView())
    }

    @PutMapping("/authors/{uuid}")
    fun updateSource(
        @PathVariable uuid: UUID,
        @Valid @RequestBody request: AuthorRequest,
    ): ResponseEntity<AuthorView>? {
        logger.debug { "Handle request PUT /authors/$uuid, body=$request" }
        val updated = authorService.update(uuid, request)
        logger.info { "Updated Author[uuid=$uuid]: $updated" }
        return ResponseEntity.ok(updated.toView())
    }

    @DeleteMapping("/authors/{uuid}")
    fun deleteSource(@PathVariable uuid: UUID): ResponseEntity<Unit> {
        logger.debug { "Handle request DELETE /authors/$uuid" }
        val deleted = authorService.delete(uuid)
        logger.info { "Updated Author[uuid=$deleted]" }
        return ResponseEntity.noContent().build()
    }
}
