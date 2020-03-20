package ru.fors.monitoring.api.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface GetActivityWorkloadUseCase {
    fun execute(activityId: Long): ActivityWorkload
}