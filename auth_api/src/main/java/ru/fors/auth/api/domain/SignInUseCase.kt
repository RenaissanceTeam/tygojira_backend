package ru.fors.auth.api.domain

import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.dto.TokenResponse

interface SignInUseCase {
    fun execute(credentials: Credentials): TokenResponse
}