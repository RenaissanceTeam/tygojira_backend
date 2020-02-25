package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest

interface GetWorkloadRequestsInitiatedByCallerUseCase {
    fun execute(): List<WorkloadRequest>
}