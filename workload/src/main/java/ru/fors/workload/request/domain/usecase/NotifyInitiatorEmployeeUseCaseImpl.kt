package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.entity.NoInitiatorException
import ru.fors.workload.api.request.domain.usecase.NotifyInitiatorEmployeeUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestObservableRepo

@Component
class NotifyInitiatorEmployeeUseCaseImpl(
        private val workloadRequestObservableRepo: WorkloadRequestObservableRepo
) : NotifyInitiatorEmployeeUseCase {
    override fun execute(request: WorkloadRequest) {
        val initiator = request.initiator ?: throw NoInitiatorException(request)

        workloadRequestObservableRepo.onInitiatedChanged(initiator, request)
    }
}