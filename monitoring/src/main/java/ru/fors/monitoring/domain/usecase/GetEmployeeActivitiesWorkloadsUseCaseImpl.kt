package ru.fors.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.usecase.GetEmployeeActivitiesWorkloadsUseCase
import ru.fors.workload.api.domain.usecase.GetActivitiesAndWorkloadsForEmployeeUseCase

@Component
class GetEmployeeActivitiesWorkloadsUseCaseImpl(
        private val getActivitiesAndWorkloadsForEmployeeUseCase: GetActivitiesAndWorkloadsForEmployeeUseCase
) : GetEmployeeActivitiesWorkloadsUseCase {

    override fun execute(employeeId: Long): List<ActivityWorkload> {
        return getActivitiesAndWorkloadsForEmployeeUseCase.execute(employeeId)
    }
}