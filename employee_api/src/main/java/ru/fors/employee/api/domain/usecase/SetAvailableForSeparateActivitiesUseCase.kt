package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.SeparateActivityAvailability
import java.time.LocalDate

interface SetAvailableForSeparateActivitiesUseCase {
    fun execute(id: Long, available: List<LocalDate>): SeparateActivityAvailability
}