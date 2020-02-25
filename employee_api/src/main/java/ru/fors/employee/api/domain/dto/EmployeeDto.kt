package ru.fors.employee.api.domain.dto

data class EmployeeDto(
        val username: String,
        val firstName: String,
        val middleName: String = "",
        val lastName: String,
        val position: String,
        val subdivision: String,
        val skills: List<String> = listOf()
)