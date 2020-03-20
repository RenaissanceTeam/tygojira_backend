package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.WorkloadRequestInDto

interface AddWorkloadRequestUseCase {
    fun execute(requestDto: WorkloadRequestInDto): WorkloadRequest
}