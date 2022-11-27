package io.perryquotes.api.author

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.perryquotes.core.author.AuthorModel
import io.perryquotes.core.author.AuthorNotFoundException
import io.perryquotes.core.author.AuthorRequest
import io.perryquotes.core.author.AuthorService
import io.perryquotes.core.exception.InvalidDataException
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime.now
import java.util.UUID.randomUUID

@WebMvcTest(AuthorController::class)
internal class AuthorControllerTest {

    @MockkBean
    lateinit var dsl: DSLContext //why is this needed?

    @MockkBean
    lateinit var authorServiceMock: AuthorService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testSetup() {
        assertThat(authorServiceMock).isNotNull
        assertThat(mockMvc).isNotNull
    }

    @Test
    fun testFindAll() {
        val authorModel1 = AuthorModel(randomUUID(), "Author1", now(), now())
        val authorModel2 = AuthorModel(randomUUID(), "Author2", now(), now())

        every { authorServiceMock.getAll() } returns listOf(authorModel1, authorModel2)

        mockMvc.perform(get("/authors"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize<Int>(2))) //TODO type??
            .andExpect(jsonPath("$[0].uuid", `is`(authorModel1.uuid.toString())))
            .andExpect(jsonPath("$[0].name", `is`(authorModel1.name)))
            .andExpect(jsonPath("$[0].createdDate", notNullValue()))
            .andExpect(jsonPath("$[0].lastModifiedDate", notNullValue()))
            .andExpect(jsonPath("$[1].uuid", `is`(authorModel2.uuid.toString())))
            .andExpect(jsonPath("$[1].name", `is`(authorModel2.name)))
            .andExpect(jsonPath("$[1].createdDate", notNullValue()))
            .andExpect(jsonPath("$[1].lastModifiedDate", notNullValue()))
    }

    @Test
    fun testFindByUuid() {
        val byUUID = AuthorModel(randomUUID(), "Author1", now(), now())
        val uuid = byUUID.uuid
        every { authorServiceMock.getByUuid(uuid) } returns byUUID

        mockMvc.perform(get("/authors/$uuid"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.uuid", `is`(byUUID.uuid.toString())))
            .andExpect(jsonPath("$.name", `is`(byUUID.name)))
            .andExpect(jsonPath("$.createdDate", notNullValue()))
            .andExpect(jsonPath("$.lastModifiedDate", notNullValue()))
    }

    @Test
    fun testFindByUuidNotFound() {
        val uuid = randomUUID()
        every { authorServiceMock.getByUuid(uuid) } returns null
        mockMvc.perform(get("/authors/$uuid"))
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.exception", `is`("AuthorNotFoundException")))
            .andExpect(jsonPath("$.message", `is`("Author[uuid=$uuid] not found")))
            .andExpect(jsonPath("$.status", `is`("404:NOT_FOUND")))
    }

    @Test
    fun testValidCreate() {
        val request = AuthorRequest("New Author")
        val created = AuthorModel(randomUUID(), request.name, now(), now())

        every { authorServiceMock.create(request) } returns created

        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(header().string(HttpHeaders.LOCATION, containsString("/authors/${created.uuid}")))
            .andExpect(jsonPath("$.uuid", `is`(created.uuid.toString())))
            .andExpect(jsonPath("$.name", `is`(created.name)))
            .andExpect(jsonPath("$.createdDate", notNullValue()))
            .andExpect(jsonPath("$.lastModifiedDate", notNullValue()))
    }

    @Test
    fun testCreateNameIsEmpty() {
        val request = AuthorRequest("")

        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors", hasSize<Int>(1))) //TODO type??
            .andExpect(jsonPath("$.errors[0].fieldName", `is`("name")))
            .andExpect(jsonPath("$.errors[0].code", `is`("{author.request.name.notblank}")))
            .andExpect(jsonPath("$.errors[0].msg", `is`("NotBlank")))
    }

    @Test
    fun testCreateExistingName() {
        val request = AuthorRequest("Existing name")
        val exception = InvalidDataException("Author[name=${request.name}] already exists")

        every { authorServiceMock.create(request) } throws exception

        mockMvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.exception", `is`(exception::class.simpleName)))
            .andExpect(jsonPath("$.message", `is`(exception.message)))
            .andExpect(jsonPath("$.status", `is`("${HttpStatus.BAD_REQUEST.value()}:${HttpStatus.BAD_REQUEST.name}")))
    }

    @Test
    fun testValidUpdate() {
        val request = AuthorRequest("New name")
        val updated = AuthorModel(randomUUID(), request.name, now(), now())

        every { authorServiceMock.update(updated.uuid, request) } returns updated

        mockMvc.perform(
            put("/authors/${updated.uuid}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.uuid", `is`(updated.uuid.toString())))
            .andExpect(jsonPath("$.name", `is`(updated.name)))
            .andExpect(jsonPath("$.createdDate", notNullValue()))
            .andExpect(jsonPath("$.lastModifiedDate", notNullValue()))
    }

    @Test
    fun testUpdateNameIsEmpty() {
        val request = AuthorRequest("")

        mockMvc.perform(
            put("/authors/${randomUUID()}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.errors", hasSize<Int>(1))) //TODO type??
            .andExpect(jsonPath("$.errors[0].fieldName", `is`("name")))
            .andExpect(jsonPath("$.errors[0].code", `is`("{author.request.name.notblank}")))
            .andExpect(jsonPath("$.errors[0].msg", `is`("NotBlank")))
    }

    @Test
    fun testUpdateAuthorNotFound() {

        val uuid = randomUUID()
        val request = AuthorRequest("Name")
        val exception = AuthorNotFoundException("Unable to update, Author[uuid=$uuid] not found")

        every { authorServiceMock.update(uuid, request) } throws exception

        mockMvc.perform(
            put("/authors/$uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(request))
        )
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.exception", `is`(exception::class.simpleName)))
            .andExpect(jsonPath("$.message", `is`(exception.message)))
            .andExpect(jsonPath("$.status", `is`("${HttpStatus.NOT_FOUND.value()}:${HttpStatus.NOT_FOUND.name}")))
    }

    @Test
    fun testDelete() {
        val uuid = randomUUID()
        every { authorServiceMock.delete(uuid) } returns true

        mockMvc.perform(delete("/authors/$uuid"))
            .andDo(print())
            .andExpect(status().isNoContent)
    }

    @Test
    fun testDeleteAuthorNotFound() {
        val uuid = randomUUID()
        val exception = AuthorNotFoundException("Unable to delete, Author[uuid=$uuid] not found")

        every { authorServiceMock.delete(uuid) } throws exception

        mockMvc.perform(delete("/authors/$uuid"))
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.exception", `is`(exception::class.simpleName)))
            .andExpect(jsonPath("$.message", `is`(exception.message)))
            .andExpect(jsonPath("$.status", `is`("${HttpStatus.NOT_FOUND.value()}:${HttpStatus.NOT_FOUND.name}")))
    }

    private fun asJson(obj: Any): String {
        try {
            return objectMapper.writeValueAsString(obj)
        } catch (ex: Exception) {
            fail("Unable to serialize object", ex)
        }
    }
}

