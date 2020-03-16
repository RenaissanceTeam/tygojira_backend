package ru.fors.production.calendar.api.domain.usecase

import ru.fors.entity.holiday.Holiday

interface GetHolidayByIdUseCase {
    fun execute(id: Long): Holiday
}