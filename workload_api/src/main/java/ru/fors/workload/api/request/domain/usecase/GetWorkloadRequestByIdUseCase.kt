package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest

interface GetWorkloadRequestByIdUseCase  {
    fun execute(id: Long): WorkloadRequest
}