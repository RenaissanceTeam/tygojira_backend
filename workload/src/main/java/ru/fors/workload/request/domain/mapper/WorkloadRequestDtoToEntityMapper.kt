package ru.fors.workload.request.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.WorkloadRequestOutDto

@Component
class WorkloadRequestDtoToEntityMapper {

    fun mapEntity(entity: WorkloadRequest): WorkloadRequestOutDto {
        return WorkloadRequestOutDto(
                id = entity.id,
                activity = entity.activity,
                initiator = entity.initiator,
                status = entity.status,
                targetRole = entity.targetRole,
                workUnits = entity.workUnits,
                skills = entity.skills,
                position = entity.position,
                employee = entity.employee
        )
    }
}