package ru.fors.entity.workload.monitoring

import java.time.LocalDate

data class WorkloadDifference(
        val date: LocalDate = LocalDate.now(),
        val type: WorkloadDifferenceType = WorkloadDifferenceType.NONE,
        val difference: Int
)