package ru.fors.workload.api.domain.dto

import ru.fors.entity.workload.WorkUnit


data class EmployeeWorkloadDto(
        val employeeId: Long,
        val workloadId: Long,
        val workUnits: List<WorkUnit>
)