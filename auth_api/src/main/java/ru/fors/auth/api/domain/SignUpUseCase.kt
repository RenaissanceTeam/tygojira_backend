package ru.fors.auth.api.domain

import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.entity.SystemUserRole
import ru.fors.auth.entity.User

interface SignUpUseCase {
    fun execute(credentials: Credentials, role: SystemUserRole): User
}