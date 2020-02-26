package ru.fors.workload.request.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.*
import ru.fors.workload.request.data.dto.WorkloadRequestConflictsDto
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@RestController
@RequestMapping("/workload/requests")
class WorkloadRequestController(
        private val addWorkloadRequestUseCase: AddWorkloadRequestUseCase,
        private val workloadRequestDtoToEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val workloadDtoEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val getWorkloadRequestsInitiatedByCallerUseCase: GetWorkloadRequestsInitiatedByCallerUseCase,
        private val updateWorkloadRequestUseCase: UpdateWorkloadRequestUseCase,
        private val getWorkloadRequestsAssignedToCallerUseCase: GetWorkloadRequestsAssignedToCallerUseCase,
        private val changeRequestStatusUseCase: ChangeRequestStatusUseCase,
        private val getWorkloadRequestByIdUseCase: GetWorkloadRequestByIdUseCase,
        private val getWorkloadConflictsForRequestUseCase: GetWorkloadConflictsForRequestUseCase
) {

    @PostMapping("/add")
    fun save(@RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequestDto {
        return addWorkloadRequestUseCase.execute(workloadRequestDto)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("")
    fun getAll(): List<WorkloadRequestDto> {
        return getWorkloadRequestsInitiatedByCallerUseCase.execute()
                .map(workloadDtoEntityMapper::mapEntity)
    }

    @GetMapping("/assigned")
    fun getAssigned(): List<WorkloadRequestDto> {
        return getWorkloadRequestsAssignedToCallerUseCase.execute()
                .map(workloadDtoEntityMapper::mapEntity)
    }

    @PostMapping("{id}/update")
    fun update(@PathVariable id: Long, @RequestBody workloadRequestDto: WorkloadRequestDto): WorkloadRequestDto {
        return updateWorkloadRequestUseCase.execute(id, workloadRequestDto)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @PostMapping("{id}/redirect")
    fun redirect(@PathVariable id: Long, @RequestBody workloadRequestDto: WorkloadRequestDto? = null): WorkloadRequestDto {
        workloadRequestDto?.let { updateWorkloadRequestUseCase.execute(id, workloadRequestDto) }

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.REDIRECTED)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @PostMapping("{id}/pending")
    fun pending(@PathVariable id: Long, @RequestBody workloadRequestDto: WorkloadRequestDto? = null): WorkloadRequestDto {
        workloadRequestDto?.let { updateWorkloadRequestUseCase.execute(id, workloadRequestDto) }

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.PENDING)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @PostMapping("{id}/executed")
    fun executed(@PathVariable id: Long, @RequestBody workloadRequestDto: WorkloadRequestDto? = null): WorkloadRequestDto {
        workloadRequestDto?.let { updateWorkloadRequestUseCase.execute(id, workloadRequestDto) }

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.EXECUTED)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @PostMapping("{id}/reject")
    fun reject(@PathVariable id: Long, @RequestBody workloadRequestDto: WorkloadRequestDto? = null): WorkloadRequestDto {
        workloadRequestDto?.let { updateWorkloadRequestUseCase.execute(id, workloadRequestDto) }

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.REJECTED)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long): WorkloadRequestDto {
        return getWorkloadRequestByIdUseCase.execute(id)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("{id}/conflicts")
    fun getConflicts(@PathVariable id: Long): WorkloadRequestConflictsDto {
        return getWorkloadConflictsForRequestUseCase.execute(id)
                .let { conflicts ->
                    WorkloadRequestConflictsDto(id, conflicts.flatMap { it.with.activities })
                }
    }
}