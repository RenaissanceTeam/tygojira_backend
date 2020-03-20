package ru.fors.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.usecase.GetActivitiesAndWorkloadsForEmployeeUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo

@Component
class GetActivitiesAndWorkloadsForEmployeeUseCaseImpl(
        private val repo: ActivityWorkloadRepo
) : GetActivitiesAndWorkloadsForEmployeeUseCase {

    override fun execute(employeeId: Long): List<ActivityWorkload> {
        return repo.findByEmployeeId(employeeId)
    }
}