package ru.fors.entity.workload

import ru.fors.entity.activity.Activity

data class EmployeeActivityWork(
        val activity: Activity,
        val workUnits: List<WorkUnit>
)