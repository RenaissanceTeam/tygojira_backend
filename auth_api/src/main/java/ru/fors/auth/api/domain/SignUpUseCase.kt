package ru.fors.auth.api.domain

import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.auth.User

interface SignUpUseCase {
    fun execute(credentials: Credentials, role: SystemUserRole): User
}