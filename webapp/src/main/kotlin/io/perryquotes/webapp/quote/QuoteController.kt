package io.perryquotes.webapp.quote

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class QuoteController {

    companion object : KLogging()

    @GetMapping("/quotes")
    fun listQuotes(): String {
        logger.debug { "GET /quotes, QuoteController.listQuotes" }
        return "quotes/quote-list"
    }

    @GetMapping("/quotes/author")
    fun listQuotesByAuthor(@RequestParam name:String, model: Model): String {
        logger.debug { "GET /quotes/author?name=$name, QuoteController.listQuotesByAuthor" }
        model["selected_author"] = name
        return "quotes/author-quote-list"
    }

}
