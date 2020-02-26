package ru.fors.workload.api.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface GetActivityWorkloadByActivityIdUseCase {
    fun execute(id: Long): ActivityWorkload
}