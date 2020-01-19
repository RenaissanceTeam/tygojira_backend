package ru.fors.employee.api.domain

import ru.fors.entity.employee.EmployeeRole

interface GetEmployeeRoleUseCase {
    fun execute(username: String): EmployeeRole?
}