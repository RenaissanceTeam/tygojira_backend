package ru.fors.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.ActivityWorkload
import ru.fors.monitoring.api.domain.usecase.CalculateEmployeesWorkloadPercentageOnActivityUseCase
import kotlin.math.min

@Component
class CalculateEmployeesWorkloadPercentageOnActivityUseCaseImpl : CalculateEmployeesWorkloadPercentageOnActivityUseCase {

    override fun execute(activityWorkload: ActivityWorkload): Map<Employee, Float> {
        var maxHours = 0
        return activityWorkload.workloads.associate { Pair(it.employee, it.workunits) }
                .mapValues {
                    it.value.fold(0) { acc, workUnit -> acc + workUnit.hours }
                            .also { hours -> if (hours > maxHours) maxHours = hours }
                }.mapValues { min((it.value.toFloat() / maxHours) * 100, 100f) }
    }
}