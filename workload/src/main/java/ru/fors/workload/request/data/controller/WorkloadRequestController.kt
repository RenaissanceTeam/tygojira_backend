package ru.fors.workload.request.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.withEntityExceptionsMapper
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.SaveWorkloadRequestUseCase

@RestController
@RequestMapping("/workload/requests")
class WorkloadRequestController(
        private val saveWorkloadRequestUseCase: SaveWorkloadRequestUseCase
) {

    @PostMapping("/add")
    fun save(@RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequest {
        return saveWorkloadRequestUseCase.runCatching { execute(workloadRequestDto) }
                .withEntityExceptionsMapper()
                .getOrThrow()
    }
}

@RestController
class Test {

    @GetMapping("/asdfsaf")
    fun su() {}
}