package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.SaveWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@Component
class SaveWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val workloadMapper: WorkloadRequestDtoToEntityMapper
) : SaveWorkloadRequestUseCase {

    override fun execute(requestDto: WorkloadRequestDto): WorkloadRequest {
        val request = workloadMapper.mapDto(requestDto)
        return repo.save(request)
    }
}