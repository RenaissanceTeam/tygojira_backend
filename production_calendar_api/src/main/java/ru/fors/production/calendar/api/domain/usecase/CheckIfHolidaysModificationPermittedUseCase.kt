package ru.fors.production.calendar.api.domain.usecase

import ru.fors.entity.holiday.Holiday

interface CheckIfHolidaysModificationPermittedUseCase {
    fun execute(holiday: Holiday)
}