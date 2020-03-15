package ru.fors.workload.request.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.api.request.domain.usecase.NotifyEmployeeOfAssignedRequestUseCase
import ru.fors.workload.api.request.domain.usecase.NotifyInitiatorEmployeeUseCase
import ru.fors.workload.api.request.domain.usecase.UpdateWorkloadRequestUseCase
import ru.fors.workload.api.request.domain.usecase.ValidateWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper
import ru.fors.workload.request.domain.mapper.WorkloadRequestPositionDtoToEntityMapper

@Component
class UpdateWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val positionMapper: WorkloadRequestPositionDtoToEntityMapper,
        private val requestMapper: WorkloadRequestDtoToEntityMapper,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val checkCallerHasBusinessRoleUseCase: CheckUserHasBusinessRoleUseCase,
        private val validateWorkload: ValidateWorkloadRequestUseCase,
        private val roleChecker: RoleChecker,
        private val checkIfEmployeeIsFromCallerSubdivisionUseCase: CheckIfEmployeeIsFromCallerSubdivisionUseCase,
        private val notifyEmployeeOfAssignedRequestUseCase: NotifyEmployeeOfAssignedRequestUseCase,
        private val notifyInitiatorEmployeeUseCase: NotifyInitiatorEmployeeUseCase
) : UpdateWorkloadRequestUseCase {

    override fun execute(id: Long, request: WorkloadRequestDto): WorkloadRequest {
        roleChecker.requireAny(Role.PROJECT_LEAD, Role.LINEAR_LEAD)

        val saved = repo.findByIdOrNull(id) ?: throw NoWorkloadFoundException(id)

        checkIsAllowedToUpdate(requestMapper.mapDto(request), saved)


        val updated = saved.copy(positions = request.positions.map(positionMapper::mapDto))
                .let { updated -> copyDeletedPositionsWithActiveFalse(saved, updated) }
                .let { updated -> addSavedDeletedPositions(saved, updated) }
        validateWorkload.execute(updated)

        return repo.save(updated).also {
            notifyEmployeeOfAssignedRequestUseCase.execute(it)
            notifyInitiatorEmployeeUseCase.execute(it)
        }
    }

    private fun copyDeletedPositionsWithActiveFalse(saved: WorkloadRequest, updated: WorkloadRequest): WorkloadRequest {
        val updatedPositions = updated.positions.filter { it.employee != null }.map { it.id }
        val deleted = saved.positions.filterNot { it.id in updatedPositions }

        val withDeletedPositions = updated.positions + deleted.map {
            it.copy(active = false, workUnits = listOf())
        }

        return updated.copy(positions = withDeletedPositions)
    }

    private fun addSavedDeletedPositions(saved: WorkloadRequest, updated: WorkloadRequest): WorkloadRequest {
        val updatedPositions = updated.positions.map { it.id }
        val savedDeleted = saved.positions.filterNot { it.active }

        val withOldDeletedPositions = updated.positions + savedDeleted.filterNot { it.id in updatedPositions }

        return updated.copy(positions = withOldDeletedPositions)
    }

    private fun checkIsAllowedToUpdate(updateRequest: WorkloadRequest, savedRequest: WorkloadRequest) {
        if (checkCallerHasBusinessRoleUseCase.execute(Role.LINEAR_LEAD)) {
            checkIfLinearLeadIsAllowedToUpdate(savedRequest, updateRequest)
        } else if (checkCallerHasBusinessRoleUseCase.execute(Role.PROJECT_LEAD)) {
            checkIfProjectLeadIsAllowedToUpdate(updateRequest, savedRequest)
        }
    }

    private fun checkIfProjectLeadIsAllowedToUpdate(updateRequest: WorkloadRequest, savedRequest: WorkloadRequest) {
        val caller = getCallingEmployeeUseCase.execute()

        if (savedRequest.initiator != caller) throw UpdateWorkloadNotAllowedException("Project Lead can only update his requests")
        if (changedEmployeeFromOtherSubdivision(updateRequest, savedRequest)) {
            throw UpdateWorkloadNotAllowedException("Project Lead cannot change employee from other subdivision")
        }
    }

    private fun checkIfLinearLeadIsAllowedToUpdate(savedRequest: WorkloadRequest, updateRequest: WorkloadRequest) {
        if (changedEmployeeFromOtherSubdivision(updateRequest, savedRequest)) {
            throw UpdateWorkloadNotAllowedException("Linear lead cannot change employee from other subdivision")
        }
    }

    private fun changedEmployeeFromOtherSubdivision(changeRequest: WorkloadRequest,
                                                    savedRequest: WorkloadRequest): Boolean {
        val changedOtherEmployee = changeRequest.positions.any { changePosition ->
            val employeeId = changePosition.employee?.id ?: return false

            checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employeeId).not()
                    && savedRequest.positions.find { it.id == changePosition.id } != changePosition
        }

        val deletedOtherEmployee = (savedRequest.positions - changeRequest.positions)
                .filter {
                    val employeeId = it.employee?.id ?: return@filter false
                    checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employeeId).not()
                }
                .isNotEmpty()

        return changedOtherEmployee or deletedOtherEmployee
    }
}