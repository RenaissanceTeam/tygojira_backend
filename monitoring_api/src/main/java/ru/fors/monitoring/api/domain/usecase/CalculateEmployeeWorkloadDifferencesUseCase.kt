package ru.fors.monitoring.api.domain.usecase

import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.monitoring.WorkloadDifference

interface CalculateEmployeeWorkloadDifferencesUseCase {
    fun execute(employeeWorkUnits: List<WorkUnit>): List<WorkloadDifference>
}