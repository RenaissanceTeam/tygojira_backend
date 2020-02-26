package ru.fors.workload.request.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.workload.request.WorkloadRequestPosition
import ru.fors.util.mapper.DtoEntityMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestPositionDto

@Component
class WorkloadRequestPositionDtoToEntityMapper(
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : DtoEntityMapper<WorkloadRequestPosition, WorkloadRequestPositionDto> {

    override fun mapEntity(entity: WorkloadRequestPosition): WorkloadRequestPositionDto {
        return WorkloadRequestPositionDto(
                id = entity.id,
                position = entity.position,
                skills = entity.skills,
                workUnits = entity.workUnits,
                employeeId = entity.employee?.id,
                active = entity.active
        )
    }

    override fun mapDto(dto: WorkloadRequestPositionDto): WorkloadRequestPosition {
        return WorkloadRequestPosition(
                id = dto.id ?: NOT_DEFINED_ID,
                skills = dto.skills,
                position = dto.position,
                workUnits = dto.workUnits,
                employee = dto.employeeId?.let(getEmployeeByIdUseCase::execute),
                active = dto.active
        )
    }
}