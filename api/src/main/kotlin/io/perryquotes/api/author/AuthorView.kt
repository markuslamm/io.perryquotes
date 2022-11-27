package io.perryquotes.api.author

import io.perryquotes.core.author.AuthorModel
import java.time.LocalDateTime
import java.util.*

data class AuthorView(
    val uuid: UUID,
    val name: String,
    val createdDate: LocalDateTime? = null,
    val lastModifiedDate: LocalDateTime? = null)

fun AuthorModel.toView(): AuthorView = AuthorView(uuid, name, createdDate, lastModifiedDate)
fun AuthorModel.toLightView(): AuthorView = AuthorView(uuid, name)

