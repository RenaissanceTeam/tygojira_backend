package ru.fors.monitoring.api.domain.dto

import ru.fors.entity.workload.monitoring.WorkloadDifference

data class EmployeeActivitiesWorkloadsDto(
        val employeeId: Long,
        val activitiesWorkloads: List<ActivityWorkloadWithoutEmployeeDto> = emptyList(),
        val workloadDifferences: List<WorkloadDifference> = emptyList()
)