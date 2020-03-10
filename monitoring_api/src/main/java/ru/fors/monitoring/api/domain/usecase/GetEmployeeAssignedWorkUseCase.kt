package ru.fors.monitoring.api.domain.usecase

import ru.fors.entity.workload.EmployeeWork

interface GetEmployeeAssignedWorkUseCase {
    fun execute(employeeId: Long): EmployeeWork
}