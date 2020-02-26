package ru.fors.workload.api.domain.mapper

import ru.fors.entity.workload.ActivityWorkload
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto

interface ActivityWorkloadToDtoMapper {
    fun map(workload: ActivityWorkload): ActivityWorkloadDto
}
