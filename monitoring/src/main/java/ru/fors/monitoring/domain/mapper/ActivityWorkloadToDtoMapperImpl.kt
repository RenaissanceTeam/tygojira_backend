package ru.fors.monitoring.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto
import ru.fors.workload.api.domain.dto.EmployeeWorkloadDto
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToDtoMapper

@Component
class ActivityWorkloadToDtoMapperImpl : ActivityWorkloadToDtoMapper {
    override fun map(workload: ActivityWorkload): ActivityWorkloadDto {
        return ActivityWorkloadDto(
                activityId = workload.activity.id,
                employeeWorkload = workload.workloads.map {
                    EmployeeWorkloadDto(
                            workloadId = it.id,
                            employee = it.employee,
                            workUnits = it.workunits
                    )
                }
        )
    }
}