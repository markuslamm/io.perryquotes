package io.perryquotes.webapp.base

import java.util.*

interface BaseApiClient<RESPONSE: ApiResponse, REQUEST: ApiRequest> {

    fun getByUuid(uuid: UUID): RESPONSE?
    fun getAll(): List<RESPONSE>
    fun create(request: REQUEST): RESPONSE
    fun update(uuid: UUID, request: REQUEST): RESPONSE
    fun delete(uuid: UUID): RESPONSE
}
