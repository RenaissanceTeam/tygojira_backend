package ru.fors.activity.api.domain.usecase

import ru.fors.entity.activity.Activity

interface GetLeadedActivitiesUseCase {
    fun execute(): List<Activity>
}