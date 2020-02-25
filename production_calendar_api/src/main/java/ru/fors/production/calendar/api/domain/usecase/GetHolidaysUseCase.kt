package ru.fors.production.calendar.api.domain.usecase

import ru.fors.entity.holiday.Holiday

interface GetHolidaysUseCase {
    fun execute(): List<Holiday>
}