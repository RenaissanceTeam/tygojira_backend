package ru.fors.monitoring.api.domain.mapper

import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.dto.ActivityWorkloadWithoutEmployeeDto

interface ActivityWorkloadToEmployeeLessDtoMapper {
    fun map(activityWorkload: ActivityWorkload): ActivityWorkloadWithoutEmployeeDto
}
