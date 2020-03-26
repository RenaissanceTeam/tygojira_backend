package ru.fors.workload.api.domain.dto

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.WorkUnit


data class EmployeeWorkloadDto(
        val workloadId: Long,
        val employee: Employee,
        val workUnits: List<WorkUnit>,
        val workloadPercentage: Float
)