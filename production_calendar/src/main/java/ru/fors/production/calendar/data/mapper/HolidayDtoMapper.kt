package ru.fors.production.calendar.data.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.data.dto.HolidayDto

@Component
class HolidayDtoMapper {
    fun map(holiday: HolidayDto) = Holiday(
            id = holiday.id ?: NOT_DEFINED_ID,
            title = holiday.title,
            description = holiday.description,
            startDate = holiday.startDate,
            endDate = holiday.endDate ?: holiday.startDate
    )
}