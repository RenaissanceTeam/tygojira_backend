package ru.fors.employee.api.domain.dto

data class UpdateEmployeeInfoDto(
        val skills: List<String>?,
        val position: String?
)