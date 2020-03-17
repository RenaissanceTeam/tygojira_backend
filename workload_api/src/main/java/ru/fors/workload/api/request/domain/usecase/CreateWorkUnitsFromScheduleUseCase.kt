package ru.fors.workload.api.request.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.WorkUnit
import ru.fors.workload.api.request.domain.dto.WorkloadScheduleDto

interface CreateWorkUnitsFromScheduleUseCase {
    fun execute(schedule: WorkloadScheduleDto, employee: Employee?): List<WorkUnit>
}