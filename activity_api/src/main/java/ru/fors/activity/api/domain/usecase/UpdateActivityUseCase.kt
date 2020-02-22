package ru.fors.activity.api.domain.usecase

import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.entity.activity.Activity

interface UpdateActivityUseCase {
    fun execute(id: Long, activityDto: ActivityDto): Activity
}