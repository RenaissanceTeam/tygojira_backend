package ru.fors.auth.api.domain

import ru.fors.auth.entity.SystemUserRole

interface CheckCallerHasSystemRoleUseCase {
    fun execute(role: SystemUserRole): Boolean
}