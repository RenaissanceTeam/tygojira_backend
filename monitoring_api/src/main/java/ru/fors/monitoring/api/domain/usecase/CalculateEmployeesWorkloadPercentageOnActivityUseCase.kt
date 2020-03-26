package ru.fors.monitoring.api.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.ActivityWorkload

interface CalculateEmployeesWorkloadPercentageOnActivityUseCase {
    fun execute(activityWorkload: ActivityWorkload): Map<Employee, Float>
}