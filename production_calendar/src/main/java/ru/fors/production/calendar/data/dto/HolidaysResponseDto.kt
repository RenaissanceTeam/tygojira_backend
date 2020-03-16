package ru.fors.production.calendar.data.dto

import ru.fors.entity.holiday.Holiday

data class HolidaysResponseDto(
        val holidays: List<Holiday>
)