package ru.fors.workload.api.domain.dto

import ru.fors.entity.activity.Activity
import ru.fors.entity.workload.WorkUnit

data class EmployeeWorkloadOnActivityDto(
        val employeeId: Long,
        val workloadId: Long,
        val activity: Activity,
        val workUnits: List<WorkUnit>
)