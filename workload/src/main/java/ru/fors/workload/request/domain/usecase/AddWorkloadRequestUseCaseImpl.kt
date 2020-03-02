package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeBusinessRolesUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.dto.AddWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.AddWorkloadRequestUseCase
import ru.fors.workload.api.request.domain.usecase.NotifyEmployeeOfAssignedRequestUseCase
import ru.fors.workload.api.request.domain.usecase.NotifyInitiatorEmployeeUseCase
import ru.fors.workload.api.request.domain.usecase.ValidateWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@Component
class AddWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val roleChecker: RoleChecker,
        private val workloadMapper: WorkloadRequestDtoToEntityMapper,
        private val validateWorkloadRequest: ValidateWorkloadRequestUseCase,
        private val checkIfEmployeeIsFromCallerSubdivisionUseCase: CheckIfEmployeeIsFromCallerSubdivisionUseCase,
        private val getCallingEmployeeBusinessRolesUseCase: GetCallingEmployeeBusinessRolesUseCase,
        private val notifyEmployeeOfAssignedRequestUseCase: NotifyEmployeeOfAssignedRequestUseCase,
        private val notifyInitiatorEmployeeUseCase: NotifyInitiatorEmployeeUseCase

) : AddWorkloadRequestUseCase {

    override fun execute(requestDto: WorkloadRequestDto): WorkloadRequest {
        roleChecker.requireAny(Role.PROJECT_LEAD, Role.LINEAR_LEAD)

        val request = workloadMapper.mapDto(requestDto)
                .copy(
                        initiator = getCallingEmployeeUseCase.execute(),
                        targetRole = getTargetRole(),
                        status = WorkloadRequestStatus.SENT
                )

        throwIfContainsEmployeeFromOtherSubdivision(request)
        validateWorkloadRequest.execute(request)

        return repo.save(request).also {
            notifyEmployeeOfAssignedRequestUseCase.execute(it)
            notifyInitiatorEmployeeUseCase.execute(it)
        }
    }

    private fun getTargetRole(): String {
        val roles = getCallingEmployeeBusinessRolesUseCase.execute()
        if (roles.contains(Role.LINEAR_LEAD)) return Role.PROJECT_OFFICE.toString()

        return Role.LINEAR_LEAD.toString()
    }

    private fun throwIfContainsEmployeeFromOtherSubdivision(request: WorkloadRequest) {
        request.positions.filter {
            val employeeId = it.employee?.id ?: return@filter false

            !checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employeeId)
        }.takeIf { it.isNotEmpty() }?.let {
            throw AddWorkloadNotAllowedException("Can't add employees from other subdivision")
        }
    }
}