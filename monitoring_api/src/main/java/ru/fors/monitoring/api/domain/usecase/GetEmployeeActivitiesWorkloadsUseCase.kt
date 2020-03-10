package ru.fors.monitoring.api.domain.usecase

import ru.fors.entity.workload.ActivityWorkload

interface GetEmployeeActivitiesWorkloadsUseCase {
    fun execute(employeeId: Long): List<ActivityWorkload>
}