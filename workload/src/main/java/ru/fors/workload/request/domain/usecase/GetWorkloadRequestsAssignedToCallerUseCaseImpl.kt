package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeBusinessRolesUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestsAssignedToCallerUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class GetWorkloadRequestsAssignedToCallerUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val repo: WorkloadRequestRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val getCallingEmployeeBusinessRolesUseCase: GetCallingEmployeeBusinessRolesUseCase
) : GetWorkloadRequestsAssignedToCallerUseCase {
    override fun execute(): List<WorkloadRequest> {
        roleChecker.requireAny(Role.LINEAR_LEAD, Role.PROJECT_OFFICE)

        val employee = getCallingEmployeeUseCase.execute()
        val roles = getCallingEmployeeBusinessRolesUseCase.execute()

        return if (roles.contains(Role.PROJECT_OFFICE)) {
            repo.findByTargetRole(Role.PROJECT_OFFICE.toString())
        } else {
            repo.findByTargetRoleAndInitiatorSubdivision(Role.LINEAR_LEAD.toString(), employee.subdivision)
        }
    }
}