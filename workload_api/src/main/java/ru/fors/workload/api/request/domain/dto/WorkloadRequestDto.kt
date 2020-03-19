package ru.fors.workload.api.request.domain.dto

import ru.fors.entity.activity.Activity
import ru.fors.entity.workload.request.WorkloadRequestStatus


data class WorkloadRequestDto(
        val id: Long? = null,
        val activityId: Long,
        val activity: Activity? = null,
        val positions: List<WorkloadRequestPositionDto>,
        val initiatorId: Long? = null,
        val status: WorkloadRequestStatus = WorkloadRequestStatus.NEW,
        val targetRole: String? = null
)