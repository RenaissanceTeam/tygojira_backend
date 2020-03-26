package ru.fors.monitoring.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToDtoMapper
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto
import ru.fors.workload.api.domain.dto.EmployeeWorkloadDto

@Component
class ActivityWorkloadToDtoMapperImpl : ActivityWorkloadToDtoMapper {
    override fun map(workload: ActivityWorkload, workloadPercentages: Map<Employee, Float>): ActivityWorkloadDto {
        return ActivityWorkloadDto(
                activityId = workload.activity.id,
                employeeWorkload = workload.workloads.map {
                    EmployeeWorkloadDto(
                            workloadId = it.id,
                            employee = it.employee,
                            workUnits = it.workunits,
                            workloadPercentage = workloadPercentages[it.employee] ?: TODO()
                    )
                }
        )
    }
}