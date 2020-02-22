package ru.fors.activity.api.domain.usecase

import ru.fors.entity.activity.Activity

interface UpdateActivityUseCase {
    fun execute(id: Long, activity: Activity): Activity
}