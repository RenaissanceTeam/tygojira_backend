package ru.fors.workload.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.Workload
import ru.fors.workload.api.domain.dto.AllEmployeeWorkloadsDto
import ru.fors.workload.api.domain.mapper.AllEmployeeWorkloadsToDtoMapper
import ru.fors.workload.api.domain.mapper.EmployeeWorkloadOnActivityToDtoMapper

@Component
class AllEmployeeWorkloadsToDtoMapperImpl(
        private val employeeWorkloadOnActivityToDtoMapper: EmployeeWorkloadOnActivityToDtoMapper
) : AllEmployeeWorkloadsToDtoMapper {
    override fun map(workloads: List<Workload>): AllEmployeeWorkloadsDto {
        return AllEmployeeWorkloadsDto(
                workloads = workloads.map { employeeWorkloadOnActivityToDtoMapper.map(it) }
        )
    }
}