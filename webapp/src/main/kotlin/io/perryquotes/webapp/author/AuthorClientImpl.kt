package io.perryquotes.webapp.author

import com.fasterxml.jackson.databind.ObjectMapper
import io.perryquotes.webapp.ApiProperties
import io.perryquotes.webapp.base.BaseApiClientImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

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
}
