package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Role

interface GetCallingEmployeeBusinessRolesUseCase {
    fun execute(): Set<Role>
}