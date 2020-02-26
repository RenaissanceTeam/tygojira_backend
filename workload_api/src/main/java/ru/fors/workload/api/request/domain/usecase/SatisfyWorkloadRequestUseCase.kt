package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface SatisfyWorkloadRequestUseCase {
    fun execute(id: Long): ActivityWorkload
}