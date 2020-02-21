package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto

interface SaveWorkloadRequestUseCase {
    fun execute(requestDto: WorkloadRequestDto): WorkloadRequest
}