package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetBusinessRoleByEmployeeUseCase
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.usecase.GetAssignedWorkloadRequestByEmployeeUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class GetAssignedWorkloadRequestByEmployeeUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val getBusinessRoleByEmployeeUseCase: GetBusinessRoleByEmployeeUseCase
) : GetAssignedWorkloadRequestByEmployeeUseCase {
    override fun execute(employee: Employee): List<WorkloadRequest> {
        val roles = getBusinessRoleByEmployeeUseCase.execute(employee)

        return if (roles.contains(Role.PROJECT_OFFICE)) {
            repo.findByTargetRoleOrderByStatus(Role.PROJECT_OFFICE.toString())
        } else {
            repo.findByTargetRoleAndInitiatorSubdivisionOrderByStatus(Role.LINEAR_LEAD.toString(), employee.subdivision)
        }
    }
}