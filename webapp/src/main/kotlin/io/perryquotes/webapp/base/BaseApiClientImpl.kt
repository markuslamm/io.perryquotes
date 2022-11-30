package io.perryquotes.webapp.base

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*


abstract class BaseApiClientImpl<RESPONSE, REQUEST>(private val webClient: WebClient)
    : BaseApiClient<RESPONSE, REQUEST> {

    private val responseTypeDef = object : ParameterizedTypeReference<RESPONSE>() {}
    private val responseTypeListDef = object : ParameterizedTypeReference<List<RESPONSE>>() {}

    abstract fun getApiPath(): String

    //workaround, avoid classcastexception linkedhashmap -> authorresponse e.g ?? TODO find out why
    abstract fun getResponseClass(): Class<RESPONSE>

    override fun getByUuid(uuid: UUID): RESPONSE? {

        val path = "${getApiPath()}/{uuid}"

        return webClient
                .get()
                .uri(path, uuid)
                .retrieve()
                .onStatus({ status -> status.isError }, errorHandler)
                .toEntity(getResponseClass())
                .block()?.let { response -> response.body ?: throw bodyIsNull(path) }
                ?: throw responseIsNull(path)
    }

    override fun getAll(): List<RESPONSE> {

        val path = getApiPath()

        return webClient
                .get()
                .uri(path)
                .retrieve()
                .onStatus({ status -> status.isError }, errorHandler)
                .toEntity(responseTypeListDef)
                .block()?.let { response -> response.body ?: throw bodyIsNull(path) }
                ?: throw responseIsNull(path)
    }

    override fun delete(uuid: UUID): RESPONSE {
        TODO("Not yet implemented")
    }

    override fun update(uuid: UUID, request: REQUEST): RESPONSE {
        TODO("Not yet implemented")
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
                .block()?.let { response -> response.body ?: throw bodyIsNull(path) }
                ?: throw responseIsNull(path)
    }

    //TODO
    private val errorHandler: (t: ClientResponse) -> Mono<out Throwable> = { response ->
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

    private fun responseIsNull(path: String): RuntimeException {
        return RuntimeException("ResponseEntity of GET $path is NULL")
    }

    private fun bodyIsNull(path: String): RuntimeException {
        return RuntimeException("ResponseBody of GET $path is NULL")
    }
}
