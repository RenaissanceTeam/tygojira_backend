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
import ru.fors.workload.api.domain.usecase.DeleteWorkloadOnActivityAfterDateUseCase
import java.time.LocalDate

@Component
class CloseActivityUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val deleteWorkloadOnActivityAfterDateUseCase: DeleteWorkloadOnActivityAfterDateUseCase,
        private val updateActivityUseCase: UpdateActivityUseCase
) : CloseActivityUseCase {

    override fun execute(activityId: Long, closureDate: LocalDate) {
        roleChecker.requireOne(Role.LINEAR_LEAD)
        val activity = getActivityByIdUseCase.execute(activityId)
        if(!(closureDate >= activity.startDate && closureDate < activity.endDate && closureDate <= LocalDate.now()))
            throw IllegalActivityClosureDateException()
        deleteWorkloadOnActivityAfterDateUseCase.execute(activityId, closureDate)
        updateActivityUseCase.execute(activityId, activity.toDto().copy(endDate = closureDate))
    }

    private fun Activity.toDto(): ActivityDto = ActivityDto(id, name, startDate, endDate, lead!!.id)
}