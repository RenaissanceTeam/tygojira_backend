package ru.fors.production.calendar.data.dto

import java.time.LocalDate

data class HolidayDto(
        val title: String,
        val description: String?,
        val startDate: LocalDate,
        val endDate: LocalDate?
)