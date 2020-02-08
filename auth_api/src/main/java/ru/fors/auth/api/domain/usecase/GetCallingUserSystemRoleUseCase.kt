package ru.fors.auth.api.domain.usecase

import ru.fors.entity.auth.SystemRole

interface GetCallingUserSystemRoleUseCase {
    fun execute(): SystemRole
}