package io.perryquotes.webapp.signup

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SignupController {

    companion object : KLogging()

    @GetMapping("/signup")
    fun signupForm(): String {
        logger.debug { "GET /signup, SignupController.signupForm" }
        return "signup/signup-form"
    }
}
