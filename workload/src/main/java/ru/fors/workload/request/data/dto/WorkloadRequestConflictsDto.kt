package ru.fors.workload.request.data.dto

import ru.fors.entity.activity.Activity

data class WorkloadRequestConflictsDto(
        val workloadId: Long,
        val conflictActivities: List<Activity>
)