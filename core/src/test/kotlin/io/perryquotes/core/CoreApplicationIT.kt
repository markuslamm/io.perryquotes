package io.perryquotes.core

import io.perryquotes.testutils.WithPostgresContainer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [WithPostgresContainer::class], classes = [TestCoreConfig::class])
class CoreApplicationIT {

    @Test
    fun contextLoads() {
    }
}
