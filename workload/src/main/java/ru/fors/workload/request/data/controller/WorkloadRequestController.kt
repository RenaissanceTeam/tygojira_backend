package ru.fors.workload.request.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.util.extensions.withEntityExceptionsMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestsForCallerUseCase
import ru.fors.workload.api.request.domain.usecase.SaveWorkloadRequestUseCase
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@RestController
@RequestMapping("/workload/requests")
class WorkloadRequestController(
        private val saveWorkloadRequestUseCase: SaveWorkloadRequestUseCase,
        private val workloadRequestDtoToEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val workloadDtoEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val getWorkloadRequestsForCallerUseCase: GetWorkloadRequestsForCallerUseCase
) {

    @PostMapping("/add")
    fun save(@RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequestDto {
        return saveWorkloadRequestUseCase.runCatching { execute(workloadRequestDto) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("")
    fun getAll(): List<WorkloadRequestDto> {
        return getWorkloadRequestsForCallerUseCase.runCatching { execute() }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .map(workloadDtoEntityMapper::mapEntity)
    }
}