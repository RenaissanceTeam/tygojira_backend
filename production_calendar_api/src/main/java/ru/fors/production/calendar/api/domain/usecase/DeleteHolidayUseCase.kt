package ru.fors.production.calendar.api.domain.usecase

interface DeleteHolidayUseCase {
    fun execute(id: Long)
}