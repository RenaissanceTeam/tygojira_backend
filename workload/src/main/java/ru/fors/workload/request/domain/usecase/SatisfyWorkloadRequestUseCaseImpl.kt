package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.entity.workload.Workload
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.util.extensions.requireAny
import ru.fors.util.extensions.then
import ru.fors.workload.api.request.domain.entity.NoActiveWorkloadPositionsException
import ru.fors.workload.api.request.domain.entity.NoEmployeeForPositionException
import ru.fors.workload.api.request.domain.usecase.ChangeRequestStatusUseCase
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestByIdUseCase
import ru.fors.workload.api.request.domain.usecase.SatisfyWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.ActivityWorkloadRepo

@Component
class SatisfyWorkloadRequestUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getWorkloadRequestByIdUseCase: GetWorkloadRequestByIdUseCase,
        private val activityWorkloadRepo: ActivityWorkloadRepo,
        private val changeRequestStatusUseCase: ChangeRequestStatusUseCase
) : SatisfyWorkloadRequestUseCase {
    override fun execute(id: Long): WorkloadRequest {
        roleChecker.requireAny(Role.LINEAR_LEAD, Role.PROJECT_OFFICE)
        val request = getWorkloadRequestByIdUseCase.execute(id)

        throwIfContainsPositionWithoutEmployee(request)
        throwIfNoActivePositions(request)

        activityWorkloadRepo.save(ActivityWorkload(
                activity = request.activity,
                workloads = request.positions.filter { it.active }.map { workloadPosition ->
                    Workload(
                            employee = workloadPosition.employee!!,
                            workunits = workloadPosition.workUnits.map { it.copy(id = NOT_DEFINED_ID) }
                    )
                }
        ))

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.SATISFIED)

    }

    private fun throwIfNoActivePositions(request: WorkloadRequest) {
        request.positions.none { it.active }.then { throw NoActiveWorkloadPositionsException(request.id) }
    }

    private fun throwIfContainsPositionWithoutEmployee(request: WorkloadRequest) {
        request.positions.find { it.employee == null }?.let { throw NoEmployeeForPositionException(it.id) }
    }
}