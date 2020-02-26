package ru.fors.workload.api.domain.dto

data class ActivityWorkloadDto(
        val activityId: Long,
        val employeeWorkload: List<EmployeeWorkloadDto>
)