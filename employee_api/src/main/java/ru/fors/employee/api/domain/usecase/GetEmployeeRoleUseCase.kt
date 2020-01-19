package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.EmployeeRole

interface GetEmployeeRoleUseCase {
    fun execute(username: String): EmployeeRole?
}