package ru.fors.employee.api.domain.dto

import ru.fors.entity.employee.Role

data class EmployeeWithRoleDto(
        val employee: EmployeeDto,
        val roles: List<Role>
)