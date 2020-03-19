package ru.fors.workload.api.request.domain.dto

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.WorkUnit

data class WorkloadRequestPositionDto(
        val id: Long?,
        val position: String,
        val skills: List<String>,
        val schedule: WorkloadScheduleDto? = null,
        val workUnits: List<WorkUnit>? = null,
        val employeeId: Long? = null,
        val employee: Employee? = null,
        val active: Boolean = true
)