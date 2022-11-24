package io.perryquotes.webapp.author

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.util.*

@Controller
class AuthorController(private val authorClient: AuthorClient) {

    companion object : KLogging()

    @GetMapping("/authors")
    fun listAuthors(model: Model): String {
        logger.debug { "GET /authors, AuthorController.listAuthors" }
        model["authors"] = authorClient.getAllAuthors()
        return "authors/author-list"
    }

    @GetMapping("/authors/form")
    fun createAuthorForm(model: Model): String {
        logger.debug { "GET /authors/form, AuthorController.createAuthorForm" }
        model["author"] = AuthorForm();
        return "authors/author-create-form"
    }

    @PostMapping("/authors")
    fun submitCreateForm(@ModelAttribute form: AuthorForm,): String {
        logger.debug { "POST /authors, AuthorController.submitCreateForm, form=$form" }
        return "redirect:/authors"
    }

    @GetMapping("/authors/{uuid}/form")
    fun updateAuthorForm(@PathVariable uuid: UUID, model: Model): String {
        logger.debug { "GET /authors/$uuid/form, AuthorController.updateAuthorForm" }
        val existing = authorClient.getAuthorByUuid(uuid) ?: throw IllegalArgumentException("No Author[uuid=$uuid] found")
        model["author"] = AuthorForm(existing.name, existing.uuid, existing.createdDate, existing.lastModifiedDate);
        return "authors/author-update-form"
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
