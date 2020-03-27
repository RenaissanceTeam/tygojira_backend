package ru.fors.activity.api.domain.usecase

import java.time.LocalDate

interface CloseActivityUseCase {
    fun execute(activityId: Long, closureDate: LocalDate)
}