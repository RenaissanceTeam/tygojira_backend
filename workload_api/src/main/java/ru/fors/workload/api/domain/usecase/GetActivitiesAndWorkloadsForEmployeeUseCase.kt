package ru.fors.workload.api.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface GetActivitiesAndWorkloadsForEmployeeUseCase {
    fun execute(employeeId: Long): List<ActivityWorkload>
}