package ru.fors.monitoring.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.fors.entity.workload.monitoring.WorkloadDifferenceType
import ru.fors.monitoring.api.domain.dto.EmployeeActivitiesWorkloadsDto
import ru.fors.monitoring.api.domain.mapper.ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToDtoMapper
import ru.fors.monitoring.api.domain.mapper.ActivityWorkloadToEmployeeLessDtoMapper
import ru.fors.monitoring.api.domain.usecase.CalculateEmployeeWorkloadDifferencesUseCase
import ru.fors.monitoring.api.domain.usecase.CalculateEmployeesWorkloadPercentageOnActivityUseCase
import ru.fors.monitoring.api.domain.usecase.GetActivityWorkloadUseCase
import ru.fors.monitoring.api.domain.usecase.GetEmployeeActivitiesWorkloadsUseCase
import ru.fors.workload.api.domain.dto.ActivityWorkloadDto

@RestController
@RequestMapping("/monitoring")
class MonitoringController(
        private val getEmployeeActivitiesWorkloadsUseCase: GetEmployeeActivitiesWorkloadsUseCase,
        private val getActivityWorkloadUseCase: GetActivityWorkloadUseCase,
        private val calculateEmployeeWorkloadDifferencesUseCase: CalculateEmployeeWorkloadDifferencesUseCase,
        private val calculateEmployeesWorkloadPercentageOnActivityUseCase: CalculateEmployeesWorkloadPercentageOnActivityUseCase,
        private val activityWorkloadMapper: ActivityWorkloadToDtoMapper,
        private val activityWorkloadToEmployeeLessDtoMapper: ActivityWorkloadToEmployeeLessDtoMapper,
        private val activitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper: ActivitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper
) {

    @GetMapping("/employee/{id}/workload")
    fun getEmployeeActivitiesWorkloads(@PathVariable id: Long): EmployeeActivitiesWorkloadsDto {
        return getEmployeeActivitiesWorkloadsUseCase.execute(id)
                .map(activityWorkloadToEmployeeLessDtoMapper::map)
                .let { activityWorkloadList ->
                    activitiesWorkloadsToEmployeeActivitiesWorkloadsDtoMapper.map(
                            employeeId = id,
                            activitiesWorkloads = activityWorkloadList,
                            workloadDifferences = calculateEmployeeWorkloadDifferencesUseCase.execute(activityWorkloadList.flatMap { it.workUnits })
                ) }
    }

    @GetMapping("/activity/{id}/workload")
    fun getActivityWorkload(@PathVariable id: Long): ActivityWorkloadDto {
        return getActivityWorkloadUseCase.execute(id).let {
            activityWorkloadMapper.map(
                    workload = it,
                    workloadPercentages = calculateEmployeesWorkloadPercentageOnActivityUseCase.execute(it)
            )
        }
    }
}
