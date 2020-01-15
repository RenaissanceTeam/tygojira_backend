package ru.fors.employee.api.usecase.dto

data class EmployeeWithRoleDto(
        val employee: EmployeeDto,
        val role: String
)