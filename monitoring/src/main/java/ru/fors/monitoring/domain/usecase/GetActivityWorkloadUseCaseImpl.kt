package ru.fors.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.usecase.GetActivityWorkloadUseCase
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadByActivityIdUseCase

@Component
class GetActivityWorkloadUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getActivityWorkloadByActivityIdUseCase: GetActivityWorkloadByActivityIdUseCase
) : GetActivityWorkloadUseCase {

    override fun execute(activityId: Long): ActivityWorkload {
        roleChecker.requireAny(
                Role.LINEAR_LEAD,
                Role.PROJECT_LEAD,
                Role.PROJECT_OFFICE
        )

        return getActivityWorkloadByActivityIdUseCase.execute(activityId)
    }
}