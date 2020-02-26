package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.MAXIMUM_WORKLOAD_WORKING_HOURS
import ru.fors.entity.workload.Conflict
import ru.fors.entity.workload.ConflictedWork
import ru.fors.workload.api.request.domain.usecase.GetWorkloadConflictsForRequestUseCase
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestByIdUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo
import ru.fors.workload.request.data.repo.WorkloadRepo


@Component
class GetWorkloadConflictsForRequestUseCaseImpl(
        private val repo: WorkloadRepo,
        private val activityWorkloadRepo: ActivityWorkloadRepo,
        private val getWorkloadRequestByIdUseCase: GetWorkloadRequestByIdUseCase
) : GetWorkloadConflictsForRequestUseCase {
    override fun execute(id: Long): List<Conflict> {
        return getWorkloadRequestByIdUseCase.execute(id).positions
                .filter { it.employee != null }
                .flatMap { requestedWorkLoad ->
                    val savedWorkUnits = repo.findByEmployeeId(requestedWorkLoad.employee!!.id)
                            .flatMap { it.workunits }
                            .groupBy { it.date }

                    requestedWorkLoad.workUnits.map { requestedWorkUnit ->
                        val savedWork = savedWorkUnits[requestedWorkUnit.date].orEmpty()
                        val workHoursForEmployee = savedWork.map { it.hours }.sum()

                        if (requestedWorkUnit.hours + workHoursForEmployee > MAXIMUM_WORKLOAD_WORKING_HOURS) {
                            val workloads = savedWork.map(repo::findByWorkunitsContaining)
                            val activities = workloads.map(activityWorkloadRepo::findByWorkloadsContaining).map { it!!.activity }

                            Conflict(requestedWorkUnit,
                                    ConflictedWork(
                                            workUnits = savedWork,
                                            workloads = workloads,
                                            activities = activities
                                    ))
                        } else null
                    }.filterNotNull()
                }
    }
}