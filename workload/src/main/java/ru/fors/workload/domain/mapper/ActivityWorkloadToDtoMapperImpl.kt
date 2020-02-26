package ru.fors.workload.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto
import ru.fors.workload.api.domain.dto.EmployeeWorkloadDto
import ru.fors.workload.api.domain.mapper.ActivityWorkloadToDtoMapper

@Component
class ActivityWorkloadToDtoMapperImpl : ActivityWorkloadToDtoMapper {
    override fun map(workload: ActivityWorkload): ActivityWorkloadDto {
        return ActivityWorkloadDto(
                activityId = workload.activity.id,
                employeeWorkload = workload.workloads.map {
                    EmployeeWorkloadDto(
                            workloadId = it.id,
                            employeeId = it.employee.id,
                            workUnits = it.workunits
                    )
                }
        )
    }
}