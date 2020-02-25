package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestStatus

interface ChangeRequestStatusUseCase {
    fun execute(id: Long, status: WorkloadRequestStatus): WorkloadRequest
}