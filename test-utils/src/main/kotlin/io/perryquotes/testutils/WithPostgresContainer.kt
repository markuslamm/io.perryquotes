package io.perryquotes.testutils

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class WithPostgresContainer: ApplicationContextInitializer<ConfigurableApplicationContext> {

    companion object {
        private const val imageName = "postgres:15.1-alpine"
        private val pgContainer = PostgreSQLContainer<Nothing>(imageName)
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        pgContainer.start()

        val url = "jdbc:postgresql://${pgContainer.host}:${pgContainer.firstMappedPort}/${pgContainer.databaseName}"
        val username = pgContainer.username
        val password = pgContainer.password

        TestPropertyValues.of(
            "spring.datasource.url=${url}",
            "spring.datasource.username=${username}",
            "spring.datasource.password=${password}"
        ).applyTo(configurableApplicationContext.environment)
    }
}