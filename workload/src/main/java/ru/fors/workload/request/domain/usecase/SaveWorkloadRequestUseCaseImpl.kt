package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.SaveWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@Component
class SaveWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val workloadMapper: WorkloadRequestDtoToEntityMapper
) : SaveWorkloadRequestUseCase {

    override fun execute(requestDto: WorkloadRequestDto): WorkloadRequest {
        val request = workloadMapper.mapDto(requestDto)
                .setInitiatorIdForNewRequest()

        return repo.save(request)
    }

    private fun WorkloadRequest.setInitiatorIdForNewRequest(): WorkloadRequest {
        return when (id == NOT_DEFINED_ID) {
            true -> copy(initiator = getCallingEmployeeUseCase.execute())
            false -> this
        }
    }
}