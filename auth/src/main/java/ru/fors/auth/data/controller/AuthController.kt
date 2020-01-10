package ru.fors.auth.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.SignInUseCase
import ru.fors.auth.api.domain.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.dto.TokenResponse
import ru.fors.auth.entity.SystemUserRoles

@RestController
open class AuthController(
        private val signInUseCase: SignInUseCase,
        private val signUpUseCase: SignUpUseCase
) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: Credentials): TokenResponse {
        return signInUseCase.execute(credentials)
    }

    @PostMapping("/signup/admin")
    fun registerAdmin(@RequestBody credentials: Credentials) {
        signUpUseCase.runCatching { execute(credentials, SystemUserRoles.ADMIN) }.onFailure {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody credentials: Credentials) {
        signUpUseCase.runCatching { execute(credentials, SystemUserRoles.USER) }.onFailure {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)
        }
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