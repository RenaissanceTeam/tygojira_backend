package ru.fors.production.calendar.data.dto

import ru.fors.entity.NOT_DEFINED_ID
import java.time.LocalDate

data class HolidayDto(
        val id: Long? = NOT_DEFINED_ID,
        val title: String,
        val description: String?,
        val startDate: LocalDate,
        val endDate: LocalDate?
)