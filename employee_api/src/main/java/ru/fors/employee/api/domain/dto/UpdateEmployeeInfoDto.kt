package ru.fors.employee.api.domain.dto

data class UpdateEmployeeInfoDto(
        val firstName: String?,
        val middleName: String?,
        val lastName: String?,
        val subdivision: String?,
        val skills: List<String>?,
        val position: String?
)