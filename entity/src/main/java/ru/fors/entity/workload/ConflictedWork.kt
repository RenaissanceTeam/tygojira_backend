package ru.fors.entity.workload

import ru.fors.entity.activity.Activity

data class ConflictedWork(
        val activities: List<Activity>,
        val workloads: List<Workload>,
        val workUnits: List<WorkUnit>
)