package ru.fors.production.calendar.api.domain.usecase

import ru.fors.entity.holiday.Holiday

interface AddHolidayUseCase {
    fun execute(holiday: Holiday): Holiday
}