package io.perryquotes.webapp.author

import io.perryquotes.webapp.author.AuthorClient.Author
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component
class AuthorClientImpl(private val webClient: WebClient): AuthorClient  {

    private inline fun <reified T : Any> typeRef(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}


    override fun getAllAuthors(): List<Author> {
        val response = webClient
            .get()
            .uri("/authors")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .toEntity(typeRef<List<Author>>())
            .block() ?: throw RuntimeException("Response of GET /authors is NULL")

        if(!response.statusCode.is2xxSuccessful) {
            throw RuntimeException("Unexpected HttpStatus: ${response.statusCode.value()}")
        }

        return response.body ?: throw RuntimeException("Body of GET /authors is NULL")
    }

    override fun getAuthorByUuid(uuid: UUID): Author? {
        val response = webClient
            .get()
            .uri("/authors/{uuid}", uuid)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .toEntity(typeRef<Author>())
            .block() ?: throw RuntimeException("Response of GET /authors/$uuid is NULL")

        if(!response.statusCode.is2xxSuccessful) {
            throw RuntimeException("Unexpected HttpStatus: ${response.statusCode.value()}")
        }

        return response.body ?: throw RuntimeException("Body of GET /authors/$uuid is NULL")
    }

    override fun createAuthor(authorForm: AuthorForm): Author {
        TODO("Not yet implemented")
    }

    override fun updateAuthor(uuid: UUID, authorForm: AuthorForm): Author {
        TODO("Not yet implemented")
    }
}