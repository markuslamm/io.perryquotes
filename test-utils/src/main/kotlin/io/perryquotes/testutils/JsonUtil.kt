package io.perryquotes.testutils

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.fail

class JsonUtil {

    companion object {

        fun asJson(objectMapper: ObjectMapper, obj: Any): String {
            try {
                return objectMapper.writeValueAsString(obj)
            } catch (ex: Exception) {
                fail("Unable to serialize object", ex)
            }
        }
    }
}