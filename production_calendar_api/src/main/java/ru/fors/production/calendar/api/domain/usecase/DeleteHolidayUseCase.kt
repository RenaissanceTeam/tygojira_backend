package ru.fors.production.calendar.api.domain.usecase

import ru.fors.entity.holiday.Holiday

interface DeleteHolidayUseCase {
    fun execute(holiday: Holiday)
}