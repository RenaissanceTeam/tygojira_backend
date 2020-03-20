package ru.fors.monitoring.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.monitoring.WorkloadDifference
import ru.fors.monitoring.api.domain.dto.ActivityWorkloadWithoutEmployeeDto
import ru.fors.monitoring.api.domain.dto.EmployeeActivitiesWorkloadsDto
import ru.fors.monitoring.api.domain.mapper.ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper

@Component
class ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapperImpl : ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper {

    override fun map(
            employeeId: Long,
            activitiesWorkloads: List<ActivityWorkloadWithoutEmployeeDto>,
            workloadDifferences: List<WorkloadDifference>
    ): EmployeeActivitiesWorkloadsDto {
        return EmployeeActivitiesWorkloadsDto(
                employeeId = employeeId,
                activitiesWorkloads = activitiesWorkloads,
                workloadDifferences = workloadDifferences
        )
    }
}