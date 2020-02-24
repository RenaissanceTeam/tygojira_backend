package ru.fors.workload.request.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.api.request.domain.usecase.UpdateWorkloadRequestUseCase
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
        private val roleChecker: RoleChecker,
        private val checkIfEmployeeIsFromCallerSubdivisionUseCase: CheckIfEmployeeIsFromCallerSubdivisionUseCase
) : UpdateWorkloadRequestUseCase {

    override fun execute(id: Long, request: WorkloadRequestDto): WorkloadRequest {
        roleChecker.startCheck()
                .require(Role.LINEAR_LEAD)
                .require(Role.PROJECT_LEAD)
                .requireAnySpecified()


        val saved = repo.findByIdOrNull(id) ?: throw NoWorkloadFoundException(id)

        checkIsAllowedToUpdate(requestMapper.mapDto(request), saved)

        return repo.save(saved.copy(positions = request.positions.map(positionMapper::mapDto)))
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
            val employeeId = changePosition.employeeId ?: return false

            checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employeeId).not()
                    && savedRequest.positions.find { it.id == changePosition.id } != changePosition
        }

        val deletedOtherEmployee = (savedRequest.positions - changeRequest.positions)
                .filter {
                    val employeeId = it.employeeId ?: return@filter false
                    checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employeeId).not()
                }
                .isNotEmpty()

        return changedOtherEmployee or deletedOtherEmployee
    }
}