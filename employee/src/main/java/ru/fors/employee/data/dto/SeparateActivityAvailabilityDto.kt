package ru.fors.employee.data.dto

data class SeparateActivityAvailabilityDto(
        val id: Long,
        val employeeId: Long,
        val dates: List<String>
)