package ru.fors.workload.request.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.workload.request.WorkloadRequestPosition
import ru.fors.util.mapper.DtoEntityMapper
import ru.fors.util.mapper.StringDateMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestPositionDto

@Component
class WorkloadRequestPositionDtoToEntityMapper(
        private val stringDateMapper: StringDateMapper
) : DtoEntityMapper<WorkloadRequestPosition, WorkloadRequestPositionDto> {

    override fun mapEntity(entity: WorkloadRequestPosition): WorkloadRequestPositionDto {
        return WorkloadRequestPositionDto(
                id = entity.id,
                startDate = stringDateMapper.map(entity.startDate),
                position = entity.position,
                skills = entity.skills,
                endDate = stringDateMapper.map(entity.endDate)
        )
    }

    override fun mapDto(dto: WorkloadRequestPositionDto): WorkloadRequestPosition {
        return WorkloadRequestPosition(
                id = dto.id ?: NOT_DEFINED_ID,
                skills = dto.skills,
                position = dto.position,
                startDate = stringDateMapper.map(dto.startDate),
                endDate = stringDateMapper.map(dto.endDate)
        )
    }
}