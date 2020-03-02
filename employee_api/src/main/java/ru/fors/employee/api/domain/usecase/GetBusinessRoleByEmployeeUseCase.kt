package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role

interface GetBusinessRoleByEmployeeUseCase {
    fun execute(employee: Employee): Set<Role>
}