package ru.fors.employee.api.domain.dto

data class FullEmployeeInfoDto(
        val id: Long,
        val firstName: String,
        val middleName: String,
        val lastName: String,
        val position: String,
        val subdivision: String,
        val skills: List<String>,
        val workRoles: List<String>
)