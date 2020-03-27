package ru.fors.activity.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.entity.IllegalActivityClosureDateException
import ru.fors.activity.api.domain.usecase.CloseActivityUseCase
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.activity.api.domain.usecase.UpdateActivityUseCase
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Role
import ru.fors.util.extensions.requireOne
import ru.fors.workload.api.domain.usecase.DeleteActivityWorkloadUseCase
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadByActivityIdUseCase
import java.time.LocalDate

@Component
class CloseActivityUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getActivityWorkloadByActivityIdUseCase: GetActivityWorkloadByActivityIdUseCase,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val deleteActivityWorkloadUseCase: DeleteActivityWorkloadUseCase,
        private val updateActivityUseCase: UpdateActivityUseCase
) : CloseActivityUseCase {

    override fun execute(activityId: Long, closureDate: LocalDate) {
        roleChecker.requireOne(Role.LINEAR_LEAD)
        val activity = getActivityByIdUseCase.execute(activityId)
        if(!(closureDate >= activity.startDate && closureDate < activity.endDate)) throw IllegalActivityClosureDateException()
        deleteActivityWorkloadUseCase.execute(
                getActivityWorkloadByActivityIdUseCase.execute(activityId)
        )
        updateActivityUseCase.execute(activityId, activity.toDto().copy(endDate = closureDate))
    }

    private fun Activity.toDto(): ActivityDto = ActivityDto(id, name, startDate, endDate, lead!!.id)
}