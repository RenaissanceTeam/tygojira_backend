package ru.fors.production.calendar.api.domain.usecase

import ru.fors.production.calendar.api.domain.entity.Holiday

interface GetHolidaysUseCase {
    fun execute(): List<Holiday>
}