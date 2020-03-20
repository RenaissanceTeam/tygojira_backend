package ru.fors.monitoring.api.domain.dto

import ru.fors.entity.activity.Activity
import ru.fors.entity.workload.WorkUnit

data class ActivityWorkloadWithoutEmployeeDto(
        val activity: Activity,
        val workUnits: List<WorkUnit>
)