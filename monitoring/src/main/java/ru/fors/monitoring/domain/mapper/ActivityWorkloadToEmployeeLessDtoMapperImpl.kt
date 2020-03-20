package ru.fors.monitoring.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.dto.ActivityWorkloadWithoutEmployeeDto
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToEmployeeLessDtoMapper

@Component
class ActivityWorkloadToEmployeeLessDtoMapperImpl : ActivityWorkloadToEmployeeLessDtoMapper {

    override fun map(activityWorkload: ActivityWorkload): ActivityWorkloadWithoutEmployeeDto {
        return ActivityWorkloadWithoutEmployeeDto(
                activity = activityWorkload.activity,
                workUnits = activityWorkload.workloads.flatMap { it.workunits }
        )
    }
}