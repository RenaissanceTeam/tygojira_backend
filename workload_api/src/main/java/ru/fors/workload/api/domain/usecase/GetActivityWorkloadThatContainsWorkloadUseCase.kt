package ru.fors.workload.api.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface GetActivityWorkloadThatContainsWorkloadUseCase {
    fun execute(id: Long): ActivityWorkload
}