package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.workload.request.WorkloadNotificationType
import ru.fors.workload.api.request.domain.usecase.RemoveWorkloadNotificationsForCallerUseCase
import ru.fors.workload.request.data.repo.WorkloadNotificationRepo

@Component
class RemoveWorkloadNotificationsForCallerUseCaseImpl(
        private val repo: WorkloadNotificationRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase
) : RemoveWorkloadNotificationsForCallerUseCase {

    override fun execute(type: WorkloadNotificationType) {
        repo.findByEmployeeAndType(getCallingEmployeeUseCase.execute(), type).forEach(repo::delete)
    }
}