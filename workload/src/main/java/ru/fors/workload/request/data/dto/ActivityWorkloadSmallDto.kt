package ru.fors.workload.request.data.dto

data class ActivityWorkloadSmallDto(
        val id: Long,
        val activityId: Long,
        val workloads: List<Long>
)