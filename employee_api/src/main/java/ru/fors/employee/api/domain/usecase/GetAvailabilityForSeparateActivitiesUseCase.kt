package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.SeparateActivityAvailability

interface GetAvailabilityForSeparateActivitiesUseCase {
    fun execute(id: Long): SeparateActivityAvailability
}