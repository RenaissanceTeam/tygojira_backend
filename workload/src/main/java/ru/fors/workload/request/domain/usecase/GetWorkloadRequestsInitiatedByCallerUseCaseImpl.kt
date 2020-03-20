package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestsInitiatedByCallerUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class GetWorkloadRequestsInitiatedByCallerUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val repo: WorkloadRequestRepo
) : GetWorkloadRequestsInitiatedByCallerUseCase {
    override fun execute(): List<WorkloadRequest> {
        roleChecker.requireAny(Role.PROJECT_LEAD, Role.LINEAR_LEAD)

        val employee = getCallingEmployeeUseCase.execute()
        return repo.findByInitiatorOrderByStatus(employee)
    }
}