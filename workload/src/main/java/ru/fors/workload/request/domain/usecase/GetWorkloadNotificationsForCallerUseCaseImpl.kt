package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.workload.request.WorkloadNotification
import ru.fors.entity.workload.request.WorkloadNotificationType
import ru.fors.workload.api.request.domain.usecase.GetWorkloadNotificationsForCallerUseCase
import ru.fors.workload.request.data.repo.WorkloadNotificationRepo

@Component
class GetWorkloadNotificationsForCallerUseCaseImpl(
        private val repo: WorkloadNotificationRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase
) : GetWorkloadNotificationsForCallerUseCase {
    override fun execute(type: WorkloadNotificationType): List<WorkloadNotification> {
        return repo.findByEmployeeAndType(getCallingEmployeeUseCase.execute(), type)
    }
}