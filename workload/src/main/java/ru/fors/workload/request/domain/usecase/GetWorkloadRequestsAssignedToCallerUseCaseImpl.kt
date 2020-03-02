package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.usecase.GetAssignedWorkloadRequestByEmployeeUseCase
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestsAssignedToCallerUseCase

@Component
class GetWorkloadRequestsAssignedToCallerUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val getAssignedWorkloadRequestByEmployeeUseCase: GetAssignedWorkloadRequestByEmployeeUseCase
) : GetWorkloadRequestsAssignedToCallerUseCase {
    override fun execute(): List<WorkloadRequest> {
        roleChecker.requireAny(Role.LINEAR_LEAD, Role.PROJECT_OFFICE)

        val employee = getCallingEmployeeUseCase.execute()
        return getAssignedWorkloadRequestByEmployeeUseCase.execute(employee)
    }
}