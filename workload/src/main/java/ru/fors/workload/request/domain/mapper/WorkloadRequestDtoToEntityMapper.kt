package ru.fors.workload.request.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestPosition
import ru.fors.util.StringDateMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto

@Component
class WorkloadRequestDtoToEntityMapper(
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val stringDateMapper: StringDateMapper
) {
    fun map(workload: WorkloadRequestDto): WorkloadRequest {
        return WorkloadRequest(
                id = workload.id ?: 0,
                activity = getActivityByIdUseCase.execute(workload.activityId),
                positions = workload.positions.map {
                    WorkloadRequestPosition(
                            id = it.id ?: 0,
                            position = it.position,
                            skills = it.skills,
                            startDate = stringDateMapper.map(it.startDate),
                            endDate = stringDateMapper.map(it.endDate)
                    )
                }
        )
    }
}