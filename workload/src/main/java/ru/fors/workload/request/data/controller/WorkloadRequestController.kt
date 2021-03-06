package ru.fors.workload.request.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.entity.workload.request.WorkloadNotificationType
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.workload.api.request.domain.dto.WorkloadRequestInDto
import ru.fors.workload.api.request.domain.dto.WorkloadRequestOutDto
import ru.fors.workload.api.request.domain.usecase.*
import ru.fors.workload.request.data.dto.EmployeeIdDto
import ru.fors.workload.request.data.dto.NotificationsDto
import ru.fors.workload.request.data.dto.WorkloadRequestConflictsDto
import ru.fors.workload.request.data.dto.WorkloadRequestsDto
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@RestController
@RequestMapping("/workload/requests")
class WorkloadRequestController(
        private val addWorkloadRequestUseCase: AddWorkloadRequestUseCase,
        private val workloadRequestDtoToEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val workloadDtoEntityMapper: WorkloadRequestDtoToEntityMapper,
        private val getWorkloadRequestsInitiatedByCallerUseCase: GetWorkloadRequestsInitiatedByCallerUseCase,
        private val getWorkloadRequestsAssignedToCallerUseCase: GetWorkloadRequestsAssignedToCallerUseCase,
        private val changeRequestStatusUseCase: ChangeRequestStatusUseCase,
        private val getWorkloadRequestByIdUseCase: GetWorkloadRequestByIdUseCase,
        private val getWorkloadConflictsForRequestUseCase: GetWorkloadConflictsForRequestUseCase,
        private val satisfyWorkloadRequestUseCase: SatisfyWorkloadRequestUseCase,
        private val getWorkloadNotificationsForCallerUseCase: GetWorkloadNotificationsForCallerUseCase,
        private val removeWorkloadNotificationsForCallerUseCase: RemoveWorkloadNotificationsForCallerUseCase
) {

    @PostMapping("/add")
    fun save(@RequestBody workloadRequestDto: WorkloadRequestInDto): WorkloadRequestOutDto {
        return addWorkloadRequestUseCase.execute(workloadRequestDto)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("/initiated")
    fun getAll(): WorkloadRequestsDto {
        return getWorkloadRequestsInitiatedByCallerUseCase.execute()
                .map(workloadDtoEntityMapper::mapEntity)
                .let(::WorkloadRequestsDto)
    }

    @GetMapping("/initiated/notifications")
    fun getInitiatedNotifications(): NotificationsDto {
        return getWorkloadNotificationsForCallerUseCase.execute(WorkloadNotificationType.INITIATOR)
                .map { it.id }
                .let(::NotificationsDto)
    }

    @DeleteMapping("/initiated/notifications")
    fun removeInitiatedNotifications() {
        removeWorkloadNotificationsForCallerUseCase.execute(WorkloadNotificationType.INITIATOR)
    }

    @GetMapping("/assigned")
    fun getAssigned(): WorkloadRequestsDto {
        return getWorkloadRequestsAssignedToCallerUseCase.execute()
                .map(workloadDtoEntityMapper::mapEntity)
                .let(::WorkloadRequestsDto)
    }

    @GetMapping("/assigned/notifications")
    fun getAssignedNotifications(): NotificationsDto {
        return getWorkloadNotificationsForCallerUseCase.execute(WorkloadNotificationType.ASSIGNEE)
                .map { it.id }
                .let(::NotificationsDto)
    }

    @DeleteMapping("/assigned/notifications")
    fun removeAssignedNotifications() {
        removeWorkloadNotificationsForCallerUseCase.execute(WorkloadNotificationType.ASSIGNEE)
    }


    @PostMapping("{id}/redirect")
    fun redirect(@PathVariable id: Long): WorkloadRequestOutDto {

        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.REDIRECTED)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @PostMapping("{id}/pending")
    fun pending(@PathVariable id: Long): WorkloadRequestOutDto {
        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.PENDING)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @PostMapping("{id}/satisfy")
    fun satisfy(@PathVariable id: Long, @RequestBody employee: EmployeeIdDto? = null): WorkloadRequestOutDto {
        return satisfyWorkloadRequestUseCase.execute(id, employee?.employeeId).let(
                workloadRequestDtoToEntityMapper::mapEntity
        )
    }

    @PostMapping("{id}/reject")
    fun reject(@PathVariable id: Long): WorkloadRequestOutDto {
        return changeRequestStatusUseCase.execute(id, WorkloadRequestStatus.REJECTED)
                .let(workloadRequestDtoToEntityMapper::mapEntity)
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long): WorkloadRequestOutDto {
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