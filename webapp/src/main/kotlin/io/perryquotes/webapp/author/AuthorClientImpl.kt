package io.perryquotes.webapp.author

import io.perryquotes.webapp.author.AuthorClient.Author
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component
class AuthorClientImpl(private val webClient: WebClient): AuthorClient  {

    private inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}


    override fun getAllAuthors(): ResponseEntity<List<Author>>? {
        return webClient
            .get()
            .retrieve()
            .toEntity(typeRef<List<Author>>())
            .block()
    }

    override fun getAuthorByUuid(uuid: UUID): Author? {
        TODO("Not yet implemented")
    }

    override fun createAuthor(authorForm: AuthorForm): Author {
        TODO("Not yet implemented")
    }

    override fun updateAuthor(uuid: UUID, authorForm: AuthorForm): Author {
        TODO("Not yet implemented")
    }
}