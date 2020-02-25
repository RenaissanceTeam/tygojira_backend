package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest

interface GetWorkloadRequestsAssignedToCallerUseCase {
    fun execute(): List<WorkloadRequest>
}