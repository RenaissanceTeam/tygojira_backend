package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.request.WorkloadNotification
import ru.fors.entity.workload.request.WorkloadNotificationType

interface GetWorkloadNotificationsForCallerUseCase {
    fun execute(type: WorkloadNotificationType): List<WorkloadNotification>
}