package io.perryquotes.webapp.author

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.util.*

@Controller
class AuthorController {

    companion object : KLogging()

    @GetMapping("/authors")
    fun listAuthors(): String {
        logger.debug { "GET /authors, AuthorController.listAuthors" }
        return ""
    }

    @GetMapping("/authors/form")
    fun createAuthorForm(): String {
        logger.debug { "GET /authors/form, AuthorController.createAuthorForm" }
        return ""
    }

    @PostMapping("/authors")
    fun submitCreateForm(): String {
        logger.debug { "POST /authors, AuthorController.submitCreateForm" }
        return ""
    }

    @GetMapping("/authors/{uuid}/form")
    fun updateAuthorForm(@PathVariable uuid: UUID): String {
        logger.debug { "GET /authors/$uuid/form, AuthorController.updateAuthorForm" }
        return ""
    }

    @PostMapping("/authors/{uuid}")
    fun submitUpdateForm(@PathVariable uuid: UUID): String {
        logger.debug { "POST /authors/$uuid, AuthorController.submitUpdateForm" }
        return ""
    }

    @PostMapping("/authors/{uuid}/delete")
    fun deleteAuthor(@PathVariable uuid: UUID): String {
        logger.debug { "POST /authors/$uuid/delete, AuthorController.deleteAuthor" }
        return ""

    }


}