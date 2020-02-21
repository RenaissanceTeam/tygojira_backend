package ru.fors.workload.api.request.domain.dto

import ru.fors.entity.workload.request.IDLE

data class WorkloadRequestDto(
        val id: Long? = null,
        val activityId: Long,
        val positions: List<WorkloadRequestPositionDto>,
        val initiatorId: Long? = null,
        val status: String = IDLE,
        val targetRole: String? = null
)