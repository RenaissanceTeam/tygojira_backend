package ru.fors.workload.api.request.domain.dto

import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.request.WorkloadRequestStatus


data class WorkloadRequestInDto(
        val id: Long? = null,
        val activityId: Long,
        val position: String,
        val skills: List<String>,
        val schedule: WorkloadScheduleDto,
        val employeeId: Long? = null
)

data class UpdateWorkloadRequestDto(
        val employeeId: Long? = null,
        val position: String? = null,
        val skills: List<String>? = null,
        val activityId: Long? = null
)

data class WorkloadRequestOutDto(
        val id: Long? = null,
        val activity: Activity? = null,
        val initiator: Employee,
        val status: WorkloadRequestStatus,
        val targetRole: String? = null,
        val position: String,
        val skills: List<String>,
        val workUnits: List<WorkUnit>,
        val employee: Employee? = null
)