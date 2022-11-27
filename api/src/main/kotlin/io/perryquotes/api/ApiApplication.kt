package io.perryquotes.api

import io.perryquotes.core.CoreConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(CoreConfig::class)
class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
