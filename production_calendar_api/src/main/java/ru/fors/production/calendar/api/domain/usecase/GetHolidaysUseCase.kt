package ru.fors.production.calendar.api.domain.usecase

import ru.fors.entity.holiday.Holiday
import java.time.Year

interface GetHolidaysUseCase {
    fun execute(year: Year): List<Holiday>
}