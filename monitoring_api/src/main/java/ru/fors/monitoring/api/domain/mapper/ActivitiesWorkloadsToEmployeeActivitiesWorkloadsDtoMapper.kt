package ru.fors.monitoring.api.domain.mapper

import ru.fors.entity.workload.monitoring.WorkloadDifference
import ru.fors.monitoring.api.domain.dto.ActivityWorkloadWithoutEmployeeDto
import ru.fors.monitoring.api.domain.dto.EmployeeActivitiesWorkloadsDto

interface ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper {
    fun map(
            employeeId: Long,
            activitiesWorkloads: List<ActivityWorkloadWithoutEmployeeDto>,
            workloadDifferences: List<WorkloadDifference>
    ): EmployeeActivitiesWorkloadsDto
}
