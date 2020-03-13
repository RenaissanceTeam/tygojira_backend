package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadNotification
import ru.fors.entity.workload.request.WorkloadNotificationType
import ru.fors.workload.api.request.domain.usecase.AddWorkloadNotificationForEmployeeUseCase
import ru.fors.workload.request.data.repo.WorkloadNotificationRepo

@Component
class AddWorkloadNotificationForEmployeeUseCaseImpl(
        private val repo: WorkloadNotificationRepo
) : AddWorkloadNotificationForEmployeeUseCase {
    override fun execute(employee: Employee, type: WorkloadNotificationType): WorkloadNotification {
        return repo.save(WorkloadNotification(employee = employee, type = type))
    }
}