package io.perryquotes.webapp.author

import io.perryquotes.webapp.ApiProperties
import io.perryquotes.webapp.base.BaseApiClientImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component
class AuthorClientImpl(webClient: WebClient,
                       private val apiProperties: ApiProperties)
    : BaseApiClientImpl<AuthorResponse, AuthorForm>(webClient), AuthorClient {

    override fun getApiPath(): String {
        return apiProperties.authors
    }

    override fun getResponseClass(): Class<AuthorResponse> {
        return AuthorResponse::class.java
    }

    override fun create(request: AuthorForm): AuthorResponse {
        TODO("Not yet implemented")
    }

    override fun update(uuid: UUID, request: AuthorForm): AuthorResponse {
        TODO("Not yet implemented")
    }

    override fun delete(uuid: UUID): AuthorResponse {
        TODO("Not yet implemented")
    }
}
