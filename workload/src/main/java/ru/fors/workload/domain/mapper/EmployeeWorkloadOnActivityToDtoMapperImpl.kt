package ru.fors.workload.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.Workload
import ru.fors.workload.api.domain.dto.EmployeeWorkloadOnActivityDto
import ru.fors.workload.api.domain.mapper.EmployeeWorkloadOnActivityToDtoMapper
import ru.fors.workload.api.domain.usecase.GetActivityWorkloadThatContainsWorkloadUseCase

@Component
class EmployeeWorkloadOnActivityToDtoMapperImpl(
        private val getActivityWorkloadByWorkloadIdUseCase: GetActivityWorkloadThatContainsWorkloadUseCase
) : EmployeeWorkloadOnActivityToDtoMapper {
    override fun map(workload: Workload): EmployeeWorkloadOnActivityDto {
        return EmployeeWorkloadOnActivityDto(
                employeeId = workload.employee.id,
                workloadId = workload.id,
                workUnits = workload.workunits,
                activity = getActivityWorkloadByWorkloadIdUseCase.execute(workload.id).activity
        )
    }
}