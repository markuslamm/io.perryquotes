package io.perryquotes.webapp.base

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*


abstract class BaseApiClientImpl<RESPONSE: ApiResponse, REQUEST: ApiRequest >(private val webClient: WebClient)
    : BaseApiClient<RESPONSE, REQUEST> {

    abstract fun getApiPath(): String
    abstract fun getResponseClass(): Class<RESPONSE>

    override fun getByUuid(uuid: UUID): RESPONSE? {

        val path = "${getApiPath()}/{uuid}"

        return webClient
                .get()
                .uri(path, uuid)
                .retrieve()
                .onStatus({ status -> status.isError }, errorHandler)
                .toEntity(getResponseClass())
                .block()?.let { response -> response.body ?: throw bodyIsNull(HttpMethod.GET, path) }
                ?: throw responseIsNull(HttpMethod.GET, path)
    }

    override fun getAll(): List<RESPONSE> {

        val path = getApiPath()

        return webClient
                .get()
                .uri(path)
                .retrieve()
                .onStatus({ status -> status.isError }, errorHandler)
                .toEntityList(getResponseClass())
                .block()?.let { response -> response.body ?: throw bodyIsNull(HttpMethod.GET, path) }
                ?: throw responseIsNull(HttpMethod.GET, path)
    }

    override fun delete(uuid: UUID): RESPONSE {
        TODO("Not yet implemented")
    }

    override fun update(uuid: UUID, request: REQUEST): RESPONSE {
        val path = "${getApiPath()}/{uuid}"

        return webClient
            .put()
            .uri(path, uuid)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus({ status -> status.isError }, errorHandler)
            .toEntity(getResponseClass())
            .block()?.let { response -> response.body ?: throw bodyIsNull(HttpMethod.PUT, path) }
            ?: throw responseIsNull(HttpMethod.PUT, path)

    }

    override fun create(request: REQUEST): RESPONSE {
        val path = getApiPath()

        return webClient
                .post()
                .uri(path)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus({ status -> status.isError }, errorHandler)
                .toEntity(getResponseClass())
                .block()?.let { response -> response.body ?: throw bodyIsNull(HttpMethod.POST, path) }
                ?: throw responseIsNull(HttpMethod.PUT, path)
    }

    //TODO
    private val errorHandler: (t: ClientResponse) -> Mono<Throwable> = { response ->
        when (response.statusCode()) {
            HttpStatus.BAD_REQUEST -> Mono.error(Exception("bad request made"))
            HttpStatus.UNAUTHORIZED, HttpStatus.FORBIDDEN -> Mono.error(Exception("auth error"))
            HttpStatus.NOT_FOUND -> Mono.error(Exception("Maybe not an error?"))
            HttpStatus.INTERNAL_SERVER_ERROR -> Mono.error(Exception("server error"))
            else -> {
                Mono.error(Exception("something went wrong"))
            }
        }
    }

    private fun responseIsNull(httpMethod: HttpMethod, path: String): RuntimeException {
        return RuntimeException("ResponseEntity of ${httpMethod.name()} $path is NULL")
    }

    private fun bodyIsNull(httpMethod: HttpMethod, path: String): RuntimeException {
        return RuntimeException("ResponseBody of GET ${httpMethod.name()} is NULL")
    }
}
