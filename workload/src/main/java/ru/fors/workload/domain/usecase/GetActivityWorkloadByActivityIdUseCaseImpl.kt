package ru.fors.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.entity.NoWorkloadForActivityException
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadByActivityIdUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo

@Component
class GetActivityWorkloadByActivityIdUseCaseImpl(
        private val repo: ActivityWorkloadRepo
) : GetActivityWorkloadByActivityIdUseCase {
    override fun execute(id: Long): ActivityWorkload {
        return repo.findByActivityId(id) ?: throw NoWorkloadForActivityException(id)
    }
}