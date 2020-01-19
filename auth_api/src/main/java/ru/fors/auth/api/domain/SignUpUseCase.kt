package ru.fors.auth.api.domain

import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.entity.SystemUserRoles

interface SignUpUseCase {
    fun execute(credentials: Credentials, role: SystemUserRoles)
}