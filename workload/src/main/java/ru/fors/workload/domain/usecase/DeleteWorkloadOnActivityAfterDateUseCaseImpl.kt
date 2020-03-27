package ru.fors.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.usecase.DeleteWorkloadOnActivityAfterDateUseCase
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadByActivityIdUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo
import java.time.LocalDate

@Component
class DeleteWorkloadOnActivityAfterDateUseCaseImpl(
        private val getActivityWorkloadByActivityIdUseCase: GetActivityWorkloadByActivityIdUseCase,
        private val activityWorkloadRepo: ActivityWorkloadRepo
) : DeleteWorkloadOnActivityAfterDateUseCase {

    override fun execute(activityId: Long, closureDate: LocalDate) {
        val activityWorkload = getActivityWorkloadByActivityIdUseCase.execute(activityId)
        require(activityWorkload.activity.startDate <= closureDate && closureDate <= activityWorkload.activity.endDate)
        val updatedActivityWorkload = ActivityWorkload(
                id = activityWorkload.id,
                activity = activityWorkload.activity,
                workloads = activityWorkload.workloads.map {
                    it.copy(workunits = it.workunits.filterNot { workUnit -> workUnit.date > closureDate })
                }
        )
        activityWorkloadRepo.save(updatedActivityWorkload)
    }
}