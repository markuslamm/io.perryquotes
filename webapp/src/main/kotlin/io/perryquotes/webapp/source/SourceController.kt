package io.perryquotes.webapp.source

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SourceController {

    companion object : KLogging()

    @GetMapping("/sources")
    fun listSources(): String {
        logger.debug { "GET /sources, SourceController.listSources" }
        return "sources/source-list"
    }
}
