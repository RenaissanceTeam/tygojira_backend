package ru.fors.employee.api.domain

import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto

interface AddEmployeeUseCase {
    fun execute(dto: EmployeeWithRoleDto): Employee
}