package ru.fors.monitoring.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.monitoring.api.domain.dto.ActivityWorkloadWithoutEmployeeDto
import ru.fors.monitoring.api.domain.dto.EmployeeActivitiesWorkloadsDto
import ru.fors.monitoring.api.domain.mapper.ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper

@Component
class ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapperImpl : ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper {

    override fun map(employeeId: Long, activitiesWorkloads: List<ActivityWorkloadWithoutEmployeeDto>): EmployeeActivitiesWorkloadsDto {
        return EmployeeActivitiesWorkloadsDto(
                employeeId = employeeId,
                activitiesWorkloads = activitiesWorkloads
        )
    }
}