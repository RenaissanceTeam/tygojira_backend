package ru.fors.workload.request.data.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.fors.util.extensions.withEntityExceptionsMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.SaveWorkloadRequestUseCase
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@RestController
@RequestMapping("/workload/requests")
class WorkloadRequestController(
        private val saveWorkloadRequestUseCase: SaveWorkloadRequestUseCase,
        private val workloadRequestDtoToEntityMapper: WorkloadRequestDtoToEntityMapper
) {

    @PostMapping("/add")
    fun save(@RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequestDto {
        return saveWorkloadRequestUseCase.runCatching { execute(workloadRequestDto) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }
}