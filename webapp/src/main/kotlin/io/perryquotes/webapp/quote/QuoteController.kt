package io.perryquotes.webapp.quote

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class QuoteController {

    companion object : KLogging()

    @GetMapping("/quotes")
    fun listQuotes(): String {
        logger.debug { "GET /quotes, QuoteController.listQuotes" }
        return "quotes/quote-list"
    }
}
