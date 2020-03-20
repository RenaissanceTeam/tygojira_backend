package ru.fors.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.usecase.GetEmployeeActivitiesWorkloadsUseCase
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.domain.usecase.GetActivitiesAndWorkloadsForEmployeeUseCase

@Component
class GetEmployeeActivitiesWorkloadsUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getActivitiesAndWorkloadsForEmployeeUseCase: GetActivitiesAndWorkloadsForEmployeeUseCase
) : GetEmployeeActivitiesWorkloadsUseCase {

    override fun execute(employeeId: Long): List<ActivityWorkload> {
        roleChecker.requireAny(
                Role.LINEAR_LEAD,
                Role.PROJECT_LEAD,
                Role.PROJECT_OFFICE
        )

        return getActivitiesAndWorkloadsForEmployeeUseCase.execute(employeeId).map {
            it.copy(
                    id = it.id,
                    activity = it.activity,
                    workloads = it.workloads.filter { workload -> workload.employee.id == employeeId }
            )
        }
    }
}