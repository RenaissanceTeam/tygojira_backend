package ru.fors.employee.api.domain

import ru.fors.employee.EmployeeRole

interface GetEmployeeRoleUseCase {
    fun execute(username: String): EmployeeRole?
}