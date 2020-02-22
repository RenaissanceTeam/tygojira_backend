package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestsForCallerUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class GetWorkloadRequestsForCallerUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val repo: WorkloadRequestRepo
) : GetWorkloadRequestsForCallerUseCase {
    override fun execute(): List<WorkloadRequest> {
        roleChecker.startCheck()
                .require(Role.LINEAR_LEAD)
                .require(Role.PROJECT_LEAD)
                .requireAnySpecified()

        val employee = getCallingEmployeeUseCase.execute()
        return repo.findByInitiator(employee)
    }
}