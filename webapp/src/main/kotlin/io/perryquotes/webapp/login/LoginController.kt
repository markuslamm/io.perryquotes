package io.perryquotes.webapp.login

import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {

    companion object : KLogging()

    @GetMapping("/login")
    fun loginForm(): String {
        logger.debug { "GET /login, LoginController.loginForm" }
        return "login/login-form"
    }
}
