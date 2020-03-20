package ru.fors.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.MAXIMUM_WORKLOAD_WORKING_HOURS
import ru.fors.entity.workload.WorkUnit
import ru.fors.entity.workload.monitoring.WorkloadDifference
import ru.fors.entity.workload.monitoring.WorkloadDifferenceType
import ru.fors.monitoring.api.domain.usecase.CalculateEmployeeWorkloadDifferencesUseCase
import kotlin.math.abs

@Component
class CalculateEmployeeWorkloadDifferencesUseCaseImpl : CalculateEmployeeWorkloadDifferencesUseCase {

    override fun execute(employeeWorkUnits: List<WorkUnit>): List<WorkloadDifference> {
        return employeeWorkUnits.groupBy { it.date }
                .map {
                    val hoursDifference = it.value.fold(MAXIMUM_WORKLOAD_WORKING_HOURS) { acc, next -> acc - next.hours }
                    WorkloadDifference(
                            it.key,
                            hoursDifference.toWorkloadDifferenceType(),
                            abs(hoursDifference)
                    )
                }
    }

    private fun Int.toWorkloadDifferenceType(): WorkloadDifferenceType {
        return when {
            this < 0 -> WorkloadDifferenceType.OVERLOAD
            this > 0 -> WorkloadDifferenceType.IDLE
            this == 0 -> WorkloadDifferenceType.FIT
            else -> throw IllegalStateException()
        }
    }
}