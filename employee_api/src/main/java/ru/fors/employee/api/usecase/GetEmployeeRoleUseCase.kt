package ru.fors.employee.api.usecase

import ru.fors.employee.EmployeeRole

interface GetEmployeeRoleUseCase {
    fun execute(username: String): EmployeeRole?
}