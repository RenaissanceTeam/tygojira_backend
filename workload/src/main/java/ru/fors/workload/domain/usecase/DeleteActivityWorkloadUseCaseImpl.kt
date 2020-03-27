package ru.fors.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.usecase.DeleteActivityWorkloadUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo

@Component
class DeleteActivityWorkloadUseCaseImpl(
        private val workloadRepo: ActivityWorkloadRepo
) : DeleteActivityWorkloadUseCase {

    override fun execute(activityWorkload: ActivityWorkload) {
        workloadRepo.delete(activityWorkload)
    }
}