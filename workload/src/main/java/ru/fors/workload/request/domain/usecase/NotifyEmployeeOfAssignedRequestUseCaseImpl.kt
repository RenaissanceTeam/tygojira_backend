package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetEmployeesWithRoleInSubdivisionUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.entity.NoInitiatorException
import ru.fors.workload.api.request.domain.usecase.NotifyEmployeeOfAssignedRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestObservableRepo

@Component
class NotifyEmployeeOfAssignedRequestUseCaseImpl(
        private val workloadRequestObservableRepo: WorkloadRequestObservableRepo,
        private val getEmployeesWithRoleInSubdivisionUseCase: GetEmployeesWithRoleInSubdivisionUseCase
) : NotifyEmployeeOfAssignedRequestUseCase {
    override fun execute(request: WorkloadRequest) {
        val initiator = request.initiator ?: throw NoInitiatorException(request)

        val toNotify = when (request.targetRole) {
            Role.PROJECT_OFFICE.name -> getEmployeesWithRoleInSubdivisionUseCase.execute(Role.PROJECT_OFFICE)
            Role.LINEAR_LEAD.name -> getEmployeesWithRoleInSubdivisionUseCase.execute(Role.LINEAR_LEAD, initiator.subdivision)
            else -> listOf()
        }

        toNotify.forEach { workloadRequestObservableRepo.onAssigned(it, request) }
    }
}