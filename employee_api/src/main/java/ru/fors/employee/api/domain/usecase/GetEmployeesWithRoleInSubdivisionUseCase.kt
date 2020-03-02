package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role

interface GetEmployeesWithRoleInSubdivisionUseCase {
    fun execute(role: Role, subdivision: String? = null): List<Employee>
}