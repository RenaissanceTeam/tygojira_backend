package ru.fors.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.Workload
import ru.fors.workload.api.domain.usecase.GetAllEmployeeWorkloadsUseCase
import ru.fors.workload.request.data.repo.WorkloadRepo

@Component
class GetAllEmployeeWorkloadsUseCaseImpl(
        private val workloadRepo: WorkloadRepo
) : GetAllEmployeeWorkloadsUseCase {
    override fun execute(id: Long): List<Workload> {
        return workloadRepo.findByEmployeeId(id)
    }
}