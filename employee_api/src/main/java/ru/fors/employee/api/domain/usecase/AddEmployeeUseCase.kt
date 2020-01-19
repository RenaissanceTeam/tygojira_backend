package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto

interface AddEmployeeUseCase {
    fun execute(dto: EmployeeWithRoleDto): Employee
}