package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.Workload
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadByActivityIdUseCase
import ru.fors.workload.api.request.domain.entity.NoEmployeeForWorkloadException
import ru.fors.workload.api.request.domain.usecase.ChangeRequestStatusUseCase
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestByIdUseCase
import ru.fors.workload.api.request.domain.usecase.SatisfyWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class SatisfyWorkloadRequestUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getWorkloadRequestByIdUseCase: GetWorkloadRequestByIdUseCase,
        private val activityWorkloadRepo: ActivityWorkloadRepo,
        private val changeRequestStatusUseCase: ChangeRequestStatusUseCase,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
        private val getActivityWorkloadByActivityIdUseCase: GetActivityWorkloadByActivityIdUseCase,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val workloadRequestRepo: WorkloadRequestRepo

) : SatisfyWorkloadRequestUseCase {
    override fun execute(id: Long, employeeId: Long?): WorkloadRequest {
        roleChecker.requireAny(Role.LINEAR_LEAD, Role.PROJECT_OFFICE)
        val request = getWorkloadRequestByIdUseCase.execute(id)

        val approvedEmployee = employeeId?.let(getEmployeeByIdUseCase::execute)
                ?: request.employee
                ?: throw NoEmployeeForWorkloadException(request.id)
        workloadRequestRepo.save(request.copy(employee = approvedEmployee))
        
        val savedWorkload = getOrCreateActivityWorkload(request.activity.id)

        activityWorkloadRepo.save(savedWorkload.copy(
                workloads = mergeActivityWork(savedWorkload.workloads, approvedEmployee, request.workUnits)
        ))

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.SATISFIED)
    }

    private fun getOrCreateActivityWorkload(activityId: Long): ActivityWorkload {
        return getActivityWorkloadByActivityIdUseCase.runCatching { execute(activityId) }
                .getOrDefault(
                        ActivityWorkload(
                                activity = getActivityByIdUseCase.execute(activityId),
                                workloads = listOf()
                        )
                )
    }

    private fun mergeActivityWork(workloads: List<Workload>, employee: Employee, workUnits: List<WorkUnit>): List<Workload> {
        val previousWorkForEmployee = workloads.find { it.employee == employee }
        if (previousWorkForEmployee != null) {
            return workloads.map {
                if (it != previousWorkForEmployee) it
                mergeWorkload(it, workUnits)
            }
        }
        return workloads + Workload(workunits = workUnits.map { it.copy(id = NOT_DEFINED_ID) }, employee = employee)
    }

    fun mergeWorkload(workload: Workload, workUnits: List<WorkUnit>): Workload {
        val firstNew = workUnits.first()
        val lastNew = workUnits.last()

        val savedUnits = workload.workunits

        val replaceStart = with(savedUnits) { indexOf(find { it.date >= firstNew.date }) }
                .let { if (it == -1) maxOf(savedUnits.size, 0) else it }
        val replaceEnd = with(savedUnits) { indexOf(find { it.date >= lastNew.date }) }
                .let { if (it == -1) maxOf(savedUnits.size, 0) else it }

        return workload.copy(
                workunits = (savedUnits.subList(0, replaceStart)
                        + workUnits
                        + savedUnits.subList(replaceEnd, savedUnits.size))
                        .map { it.copy(id = NOT_DEFINED_ID) }
        )
    }
}