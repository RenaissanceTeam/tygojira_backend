package ru.fors.workload.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.entity.NoActivityWorkloadContainsWorkloadException
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadThatContainsWorkloadUseCase
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo
import ru.fors.workload.request.data.repo.WorkloadRepo

@Component
class GetActivityWorkloadThatContainsWorkloadUseCaseImpl(
        private val repo: ActivityWorkloadRepo,
        private val workloadRepo: WorkloadRepo
) : GetActivityWorkloadThatContainsWorkloadUseCase {
    override fun execute(id: Long): ActivityWorkload {
        val workload = workloadRepo.findByIdOrNull(id) ?: throw NoWorkloadFoundException(id)
        return repo.findByWorkloadsContaining(workload) ?: throw NoActivityWorkloadContainsWorkloadException(id)
    }
}