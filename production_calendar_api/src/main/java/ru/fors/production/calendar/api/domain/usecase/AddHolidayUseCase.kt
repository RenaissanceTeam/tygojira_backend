package ru.fors.production.calendar.api.domain.usecase

import ru.fors.production.calendar.api.domain.entity.Holiday

interface AddHolidayUseCase {
    fun execute(holiday: Holiday)
}