package io.perryquotes.api

import io.perryquotes.testutils.WithPostgresContainer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [WithPostgresContainer::class])
class ApiApplicationIT {

	@Test
	fun contextLoads() {
	}

}
