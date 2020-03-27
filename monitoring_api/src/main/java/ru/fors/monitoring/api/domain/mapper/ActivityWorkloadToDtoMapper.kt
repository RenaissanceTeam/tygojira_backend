package ru.fors.monitoring.api.domain.mapper

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto

interface ActivityWorkloadToDtoMapper {
    fun map(workload: ActivityWorkload, workloadPercentages: Map<Employee, Float>): ActivityWorkloadDto
}
