package ru.fors.monitoring.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.fors.monitoring.api.domain.dto.EmployeeActivitiesWorkloadsDto
import ru.fors.monitoring.api.domain.mapper.ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToEmployeeLessDtoMapper
import ru.fors.monitoring.api.domain.usecase.GetEmployeeActivitiesWorkloadsUseCase
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToDtoMapper
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadByActivityIdUseCase

@RestController
@RequestMapping("/monitoring")
class MonitoringController(
        private val getEmployeeActivitiesWorkloadsUseCase: GetEmployeeActivitiesWorkloadsUseCase,
        private val getActivityWorkloadUseCase: GetActivityWorkloadByActivityIdUseCase,
        private val activityWorkloadMapper: ActivityWorkloadToDtoMapper,
        private val activityWorkloadToEmployeeLessDtoMapper: ActivityWorkloadToEmployeeLessDtoMapper,
        private val activitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper: ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper
) {

    @GetMapping("/employee/{id}/workload")
    fun getEmployeeActivitiesWorkloads(@PathVariable id: Long): EmployeeActivitiesWorkloadsDto {
        return getEmployeeActivitiesWorkloadsUseCase.execute(id)
                .map(activityWorkloadToEmployeeLessDtoMapper::map)
                .let { activitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper.map(id, it) }
    }

    @GetMapping("/activity/{id}/workload")
    fun getActivityWorkload(@PathVariable id: Long): ActivityWorkloadDto {
        return getActivityWorkloadUseCase.execute(id).let(activityWorkloadMapper::map)
    }
}
