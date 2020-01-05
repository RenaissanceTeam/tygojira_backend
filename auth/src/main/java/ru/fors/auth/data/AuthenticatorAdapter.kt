package ru.fors.auth.data

import ru.fors.auth.api.domain.dto.Credentials

interface AuthenticatorAdapter {
    fun authenticate(credentials: Credentials)
}

