package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.workload.WorkUnit
import ru.fors.workload.api.request.domain.dto.WorkloadScheduleDto

interface CreateScheduleFromWorkUnitsUseCase {
    fun execute(units: List<WorkUnit>): WorkloadScheduleDto
}