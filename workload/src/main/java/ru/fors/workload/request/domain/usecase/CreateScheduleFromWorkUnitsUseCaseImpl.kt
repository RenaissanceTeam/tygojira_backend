package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.WorkUnit
import ru.fors.workload.api.request.domain.dto.WorkloadScheduleDto
import ru.fors.workload.api.request.domain.usecase.CreateScheduleFromWorkUnitsUseCase

@Component
class CreateScheduleFromWorkUnitsUseCaseImpl : CreateScheduleFromWorkUnitsUseCase {
    override fun execute(units: List<WorkUnit>): WorkloadScheduleDto {
        TODO()
    }
}