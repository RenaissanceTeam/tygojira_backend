package ru.fors.workload.request.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.workload.request.WorkloadRequestPosition
import ru.fors.util.mapper.DtoEntityMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestPositionDto

@Component
class WorkloadRequestPositionDtoToEntityMapper : DtoEntityMapper<WorkloadRequestPosition, WorkloadRequestPositionDto> {

    override fun mapEntity(entity: WorkloadRequestPosition): WorkloadRequestPositionDto {
        return WorkloadRequestPositionDto(
                id = entity.id,
                startDate = entity.startDate,
                position = entity.position,
                skills = entity.skills,
                endDate = entity.endDate,
                employeeId = entity.employeeId
        )
    }

    override fun mapDto(dto: WorkloadRequestPositionDto): WorkloadRequestPosition {
        return WorkloadRequestPosition(
                id = dto.id ?: NOT_DEFINED_ID,
                skills = dto.skills,
                position = dto.position,
                startDate = dto.startDate,
                endDate = dto.endDate,
                employeeId = dto.employeeId
        )
    }
}