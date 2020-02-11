package ru.fors.activity.api.domain.usecase

import ru.fors.entity.activity.Activity

interface GetActivityByIdUseCase {
    fun execute(id: Long): Activity
}