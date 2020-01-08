package ru.fors.auth.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.fors.auth.api.domain.SignInUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.dto.TokenResponse

@RestController
open class AuthController(
        private val signInUseCase: SignInUseCase
) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: Credentials): TokenResponse {
        return signInUseCase.execute(credentials)
    }

    @GetMapping("/api")
    fun testAuthenticatedRequest(): String {
        return "authenticated request"
    }

    @GetMapping("/api/lr")
    fun testAuthenticatedLRRequest(): String {
        return "authenticated LR request"
    }
}