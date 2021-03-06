package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadRequestDto

interface UpdateWorkloadRequestUseCase {

    fun execute(id: Long, request: UpdateWorkloadRequestDto): WorkloadRequest
}