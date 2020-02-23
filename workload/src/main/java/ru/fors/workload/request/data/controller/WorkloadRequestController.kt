package ru.fors.workload.request.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.GetWorkloadRequestsForCallerUseCase
import ru.fors.workload.api.request.domain.usecase.AddWorkloadRequestUseCase
import ru.fors.workload.api.request.domain.usecase.UpdateWorkloadRequestUseCase
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@RestController
@RequestMapping("/workload/requests")
class WorkloadRequestController(
        private val addWorkloadRequestUseCase: AddWorkloadRequestUseCase,
        private val workloadRequestDtoToEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val workloadDtoEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val getWorkloadRequestsForCallerUseCase: GetWorkloadRequestsForCallerUseCase,
        private val updateWorkloadRequestUseCase: UpdateWorkloadRequestUseCase
) {

    @PostMapping("/add")
    fun save(@RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequestDto {
        return addWorkloadRequestUseCase.execute(workloadRequestDto)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("")
    fun getAll(): List<WorkloadRequestDto> {
        return getWorkloadRequestsForCallerUseCase.execute()
                .map(workloadDtoEntityMapper::mapEntity)
    }

    @PostMapping("{id}/update")
    fun update(@PathVariable id: Long, @RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequestDto {
        return updateWorkloadRequestUseCase.execute(id, workloadRequestDto)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }
}