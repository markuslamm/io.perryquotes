package io.perryquotes.webapp.base

import java.util.UUID

interface BaseApiClient<RESPONSE, REQUEST> {

    fun getByUuid(uuid: UUID): RESPONSE?
    fun getAll(): List<RESPONSE>
    fun create(request: REQUEST): RESPONSE
    fun update(uuid: UUID, request: REQUEST): RESPONSE
    fun delete(uuid: UUID): RESPONSE
}
