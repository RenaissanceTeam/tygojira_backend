package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.usecase.GetInitiatedWorkloadRequestsByEmployeeUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class GetInitiatedWorkloadRequestsByEmployeeUseCaseImpl(
        private val repo: WorkloadRequestRepo
) : GetInitiatedWorkloadRequestsByEmployeeUseCase {
    override fun execute(employee: Employee): List<WorkloadRequest> {
        return repo.findByInitiatorOrderByStatus(employee)
    }
}