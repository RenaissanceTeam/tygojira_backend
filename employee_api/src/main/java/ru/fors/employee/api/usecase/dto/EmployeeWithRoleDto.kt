package ru.fors.employee.api.usecase.dto

import ru.fors.employee.Role

data class EmployeeWithRoleDto(
        val employee: EmployeeDto,
        val roles: List<Role>
)