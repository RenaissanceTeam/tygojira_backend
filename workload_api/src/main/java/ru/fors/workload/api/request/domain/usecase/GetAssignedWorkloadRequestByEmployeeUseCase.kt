package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadRequest

interface GetAssignedWorkloadRequestByEmployeeUseCase {
    fun execute(employee: Employee): List<WorkloadRequest>
}