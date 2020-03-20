package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeBusinessRolesUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.entity.workload.request.WorkloadRequestStatus
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.dto.AddWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.WorkloadRequestInDto
import ru.fors.workload.api.request.domain.usecase.*
import ru.fors.workload.request.data.repo.WorkloadRequestRepo
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper


@Component
class AddWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val roleChecker: RoleChecker,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
        private val getWorkloadUnitsFromScheduleUseCase: CreateWorkUnitsFromScheduleUseCase,
        private val validateWorkloadRequest: ValidateWorkloadRequestUseCase,
        private val checkIfEmployeeIsFromCallerSubdivisionUseCase: CheckIfEmployeeIsFromCallerSubdivisionUseCase,
        private val getCallingEmployeeBusinessRolesUseCase: GetCallingEmployeeBusinessRolesUseCase,
        private val notifyEmployeeOfAssignedRequestUseCase: NotifyEmployeeOfAssignedRequestUseCase,
        private val notifyInitiatorEmployeeUseCase: NotifyInitiatorEmployeeUseCase

) : AddWorkloadRequestUseCase {

    override fun execute(requestDto: WorkloadRequestInDto): WorkloadRequest {
        roleChecker.requireAny(Role.PROJECT_LEAD, Role.LINEAR_LEAD)

        val employee = requestDto.employeeId?.let(getEmployeeByIdUseCase::execute)
        val request = WorkloadRequest(
                id = requestDto.id ?: NOT_DEFINED_ID,
                activity = getActivityByIdUseCase.execute(requestDto.activityId),
                initiator = getCallingEmployeeUseCase.execute(),
                targetRole = getTargetRole(),
                status = WorkloadRequestStatus.SENT,
                employee = employee,
                skills = requestDto.skills,
                position = requestDto.position,
                deletedEmployees = listOf(),
                workUnits = getWorkloadUnitsFromScheduleUseCase.execute(requestDto.schedule, employee)
        )

        throwIfContainsEmployeeFromOtherSubdivision(employee)
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

    private fun throwIfContainsEmployeeFromOtherSubdivision(employee: Employee?) {
        if (employee == null) return

        if (!checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employee)) {
            throw AddWorkloadNotAllowedException("Can't add employees from other subdivision")
        }
    }
}