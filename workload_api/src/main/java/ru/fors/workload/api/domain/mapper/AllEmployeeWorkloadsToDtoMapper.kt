package ru.fors.workload.api.domain.mapper

import ru.fors.entity.workload.Workload
import ru.fors.workload.api.domain.dto.AllEmployeeWorkloadsDto

interface AllEmployeeWorkloadsToDtoMapper {
    fun map(workloads: List<Workload>): AllEmployeeWorkloadsDto
}