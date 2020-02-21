package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.SeparateActivityAvailability
import java.util.*

interface SetAvailableForSeparateActivitiesUseCase {
    fun execute(id: Long, available: List<Date>): SeparateActivityAvailability
}