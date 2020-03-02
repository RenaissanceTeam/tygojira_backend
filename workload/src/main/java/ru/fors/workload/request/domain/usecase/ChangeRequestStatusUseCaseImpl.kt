package ru.fors.workload.request.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.util.extensions.requireAny
import ru.fors.util.extensions.requireOne
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.api.request.domain.usecase.ChangeRequestStatusUseCase
import ru.fors.workload.api.request.domain.usecase.NotifyEmployeeOfAssignedRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class ChangeRequestStatusUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val repo: WorkloadRequestRepo,
        private val notifyEmployeeOfAssignedRequestUseCase: NotifyEmployeeOfAssignedRequestUseCase
) : ChangeRequestStatusUseCase {

    override fun execute(id: Long, status: WorkloadRequestStatus): WorkloadRequest {
        roleChecker.requireAny(Role.LINEAR_LEAD, Role.PROJECT_OFFICE)

        val workload = repo.findByIdOrNull(id) ?: throw NoWorkloadFoundException(id)
        return repo.save(workload
                .also { if (status == WorkloadRequestStatus.PENDING) roleChecker.requireOne(Role.PROJECT_OFFICE) }
                .let {
                    when (status == WorkloadRequestStatus.REDIRECTED) {
                        true -> it.copy(targetRole = Role.PROJECT_OFFICE.toString())
                        false -> it
                    }
                }
                .let { it.copy(status = status) }
        ).also {
            notifyEmployeeOfAssignedRequestUseCase.execute(it)
        }
    }
}