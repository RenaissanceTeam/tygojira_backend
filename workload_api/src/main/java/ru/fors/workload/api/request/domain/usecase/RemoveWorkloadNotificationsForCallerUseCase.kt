package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadNotificationType

interface RemoveWorkloadNotificationsForCallerUseCase {
    fun execute(type: WorkloadNotificationType)
}