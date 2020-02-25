package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.MINIMUM_WORKLOAD_WORKING_HOURS
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.entity.WorkloadLessThanMinimumException
import ru.fors.workload.api.request.domain.usecase.ValidateWorkloadRequestUseCase

@Component
class ValidateWorkloadRequestUseCaseImpl : ValidateWorkloadRequestUseCase {

    override fun execute(request: WorkloadRequest) {
        request.positions.forEach { position ->
            position.workUnits
                    .find { it.hours < MINIMUM_WORKLOAD_WORKING_HOURS }
                    ?.let { throw WorkloadLessThanMinimumException(it) }
        }
    }
}