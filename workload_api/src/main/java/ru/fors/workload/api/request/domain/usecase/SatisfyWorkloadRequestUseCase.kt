package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadRequest

interface SatisfyWorkloadRequestUseCase {
    fun execute(id: Long, employeeId: Long?): WorkloadRequest
}