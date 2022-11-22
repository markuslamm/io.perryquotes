package io.perryquotes.webapp.about

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.util.*

@Controller
class AboutController {

    companion object : KLogging()

    @GetMapping("/about")
    fun about(): String {
        logger.debug { "GET /about, AboutController.about" }
        return "about/about"
    }
}
