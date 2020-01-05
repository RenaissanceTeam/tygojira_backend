package ru.fors.auth.domain

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.SignInUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.dto.TokenResponse
import ru.fors.auth.data.AuthenticatorAdapter
import ru.fors.auth.data.jwt.JwtTokenProvider

@Component
open class SignInUseCaseImpl(
        private val authenticator: AuthenticatorAdapter,
        private val tokenProvider: JwtTokenProvider
): SignInUseCase {
    override fun execute(credentials: Credentials): TokenResponse {
        authenticator.authenticate(credentials)

        val token = tokenProvider.generateToken(credentials.login)

        return TokenResponse(token)
    }
}