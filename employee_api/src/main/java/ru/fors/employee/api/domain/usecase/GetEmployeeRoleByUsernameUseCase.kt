package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.EmployeeRole

interface GetEmployeeRoleByUsernameUseCase {
    fun execute(username: String): EmployeeRole
}