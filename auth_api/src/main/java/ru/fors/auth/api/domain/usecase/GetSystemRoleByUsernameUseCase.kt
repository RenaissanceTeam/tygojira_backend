package ru.fors.auth.api.domain.usecase

import ru.fors.entity.auth.SystemRole

interface GetSystemRoleByUsernameUseCase {
    fun execute(username: String): SystemRole?
}