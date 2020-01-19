package ru.fors.auth.api.domain.usecase

import ru.fors.entity.auth.SystemUserRole

interface CheckCallerHasSystemRoleUseCase {
    fun execute(role: SystemUserRole): Boolean
}