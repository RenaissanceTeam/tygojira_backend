package ru.fors.activity.api.domain.usecase

import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.entity.activity.Activity

interface AddActivityUseCase {
    fun execute(activity: ActivityDto): Activity
}