package ru.fors.production.calendar.api.domain.usecase

import ru.fors.production.calendar.api.domain.entity.Holiday

interface DeleteHolidayUseCase {
    fun execute(holiday: Holiday)
}