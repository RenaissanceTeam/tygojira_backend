package ru.fors.workload.api.request.domain.dto

data class WorkloadRequestDto(
        val id: Long? = null,
        val activityId: Long,
        val positions: List<WorkloadRequestPositionDto>
)