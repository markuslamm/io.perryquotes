package io.perryquotes.webapp

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "api")
data class ApiProperties(val baseurl: String,
                         val authors: String)
