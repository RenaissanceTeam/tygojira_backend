package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Role

interface CheckUserHasBusinessRoleUseCase {
    fun execute(role: Role): Boolean
}