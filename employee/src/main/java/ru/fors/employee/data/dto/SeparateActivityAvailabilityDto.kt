package ru.fors.employee.data.dto

import java.time.LocalDate

data class SeparateActivityAvailabilityDto(
        val id: Long,
        val employeeId: Long,
        val dates: List<LocalDate>
)