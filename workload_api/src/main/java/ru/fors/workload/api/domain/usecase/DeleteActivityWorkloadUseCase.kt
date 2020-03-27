package ru.fors.workload.api.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface DeleteActivityWorkloadUseCase {
    fun execute(activityWorkload: ActivityWorkload)
}