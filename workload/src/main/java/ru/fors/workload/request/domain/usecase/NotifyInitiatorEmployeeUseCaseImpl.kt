package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.request.WorkloadNotificationType
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.entity.NoInitiatorException
import ru.fors.workload.api.request.domain.usecase.AddWorkloadNotificationForEmployeeUseCase
import ru.fors.workload.api.request.domain.usecase.NotifyInitiatorEmployeeUseCase

@Component
class NotifyInitiatorEmployeeUseCaseImpl(
        private val addWorkloadNotificationForEmployeeUseCase: AddWorkloadNotificationForEmployeeUseCase
) : NotifyInitiatorEmployeeUseCase {
    override fun execute(request: WorkloadRequest) {
        val initiator = request.initiator ?: throw NoInitiatorException(request)

        addWorkloadNotificationForEmployeeUseCase.execute(initiator, WorkloadNotificationType.INITIATOR)
    }
}