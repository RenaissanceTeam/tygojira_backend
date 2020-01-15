package ru.fors.employee.api.usecase

import ru.fors.employee.Employee
import ru.fors.employee.api.usecase.dto.EmployeeWithRoleDto

interface AddEmployeeUseCase {
    fun execute(dto: EmployeeWithRoleDto): Employee
}