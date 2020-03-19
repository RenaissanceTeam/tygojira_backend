package ru.fors.workload.request.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.mapper.DtoEntityMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto

@Component
class WorkloadRequestDtoToEntityMapper(
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
        private val workloadRequestPositionMapper: WorkloadRequestPositionDtoToEntityMapper
) : DtoEntityMapper<WorkloadRequest, WorkloadRequestDto> {


    override fun mapEntity(entity: WorkloadRequest): WorkloadRequestDto {
        return WorkloadRequestDto(
                id = entity.id,
                activityId = entity.activity.id,
                activity = entity.activity,
                positions = entity.positions.map(workloadRequestPositionMapper::mapEntity),
                initiatorId = entity.initiator?.id,
                status = entity.status,
                targetRole = entity.targetRole
        )
    }

    override fun mapDto(dto: WorkloadRequestDto): WorkloadRequest {
        return WorkloadRequest(
                id = dto.id ?: NOT_DEFINED_ID,
                activity = getActivityByIdUseCase.execute(dto.activityId),
                positions = dto.positions.map(workloadRequestPositionMapper::mapDto),
                status = dto.status,
                initiator = dto.initiatorId?.let { getEmployeeByIdUseCase.execute(it) },
                targetRole = dto.targetRole
        )
    }
}