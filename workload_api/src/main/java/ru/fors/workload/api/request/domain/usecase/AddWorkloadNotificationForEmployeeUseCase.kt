package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadNotification
import ru.fors.entity.workload.request.WorkloadNotificationType

interface AddWorkloadNotificationForEmployeeUseCase {
    fun execute(employee: Employee, type: WorkloadNotificationType): WorkloadNotification
}