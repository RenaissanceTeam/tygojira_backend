package ru.fors.workload.api.domain.mapper

import ru.fors.entity.workload.Workload
import ru.fors.workload.api.domain.dto.EmployeeWorkloadOnActivityDto

interface EmployeeWorkloadOnActivityToDtoMapper {
    fun map(workload: Workload): EmployeeWorkloadOnActivityDto
}