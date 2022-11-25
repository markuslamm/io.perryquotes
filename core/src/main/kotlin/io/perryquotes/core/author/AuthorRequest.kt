package io.perryquotes.core.author

import jakarta.validation.constraints.NotBlank

data class AuthorRequest(@field:NotBlank(message = "{author.request.name.notblank}") val name: String)



