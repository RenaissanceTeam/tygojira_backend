package ru.fors.activity.api.domain.usecase

import ru.fors.entity.activity.Activity
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest

interface GetActivitiesUseCase {
    fun execute(pageRequest: PageRequest): Page<Activity>
}