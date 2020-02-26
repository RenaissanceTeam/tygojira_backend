package ru.fors.workload.request.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestByIdUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class GetWorkloadRequestByIdUseCaseImpl(
        private val repo: WorkloadRequestRepo
) : GetWorkloadRequestByIdUseCase {
    override fun execute(id: Long): WorkloadRequest {
        return repo.findByIdOrNull(id) ?: throw NoWorkloadFoundException(id)
    }
}