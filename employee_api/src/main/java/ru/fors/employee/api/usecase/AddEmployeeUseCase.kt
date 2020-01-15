package ru.fors.employee.api.usecase

import ru.fors.employee.api.usecase.dto.EmployeeWithRoleDto

interface AddEmployeeUseCase {
    fun execute(employee: EmployeeWithRoleDto)
}