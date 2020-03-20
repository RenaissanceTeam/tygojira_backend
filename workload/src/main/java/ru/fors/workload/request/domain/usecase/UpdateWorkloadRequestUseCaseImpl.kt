package ru.fors.workload.request.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.util.extensions.requireAny
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadRequestDto
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.api.request.domain.usecase.NotifyEmployeeOfAssignedRequestUseCase
import ru.fors.workload.api.request.domain.usecase.NotifyInitiatorEmployeeUseCase
import ru.fors.workload.api.request.domain.usecase.UpdateWorkloadRequestUseCase
import ru.fors.workload.api.request.domain.usecase.ValidateWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo

@Component
class UpdateWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val checkCallerHasBusinessRoleUseCase: CheckUserHasBusinessRoleUseCase,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
        private val validateWorkload: ValidateWorkloadRequestUseCase,
        private val roleChecker: RoleChecker,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val checkIfEmployeeIsFromCallerSubdivisionUseCase: CheckIfEmployeeIsFromCallerSubdivisionUseCase,
        private val notifyEmployeeOfAssignedRequestUseCase: NotifyEmployeeOfAssignedRequestUseCase,
        private val notifyInitiatorEmployeeUseCase: NotifyInitiatorEmployeeUseCase
) : UpdateWorkloadRequestUseCase {

    override fun execute(id: Long, request: UpdateWorkloadRequestDto): WorkloadRequest {
        roleChecker.requireAny(Role.PROJECT_LEAD, Role.LINEAR_LEAD)

        val saved = repo.findByIdOrNull(id) ?: throw NoWorkloadFoundException(id)

        val updateEmployee = request.employeeId?.let(getEmployeeByIdUseCase::execute)

        checkIsAllowedToUpdate(updateEmployee, saved)

        val updated = saved.copy(
                activity = request.activityId?.let(getActivityByIdUseCase::execute) ?: saved.activity,
                employee = request.employeeId?.let(getEmployeeByIdUseCase::execute) ?: saved.employee,
                position = request.position ?: saved.position,
                skills = request.skills ?: saved.skills
        )
                .let { updated -> copyDeletedEmployees(saved, updated) }

        validateWorkload.execute(updated)

        return repo.save(updated).also {
            notifyEmployeeOfAssignedRequestUseCase.execute(it)
            notifyInitiatorEmployeeUseCase.execute(it)
        }
    }

    private fun copyDeletedEmployees(saved: WorkloadRequest, updated: WorkloadRequest): WorkloadRequest {
        if (saved.employee == updated.employee) return updated
        val deletedEmployee = saved.employee?.id?.let(getEmployeeByIdUseCase::execute) ?: return updated

        return updated.copy(deletedEmployees = saved.deletedEmployees + deletedEmployee)
    }

    private fun checkIsAllowedToUpdate(updateEmployee: Employee?, savedRequest: WorkloadRequest) {
        if (checkCallerHasBusinessRoleUseCase.execute(Role.LINEAR_LEAD)) {
            checkIfLinearLeadIsAllowedToUpdate(updateEmployee, savedRequest)
        } else if (checkCallerHasBusinessRoleUseCase.execute(Role.PROJECT_LEAD)) {
            checkIfProjectLeadIsAllowedToUpdate(updateEmployee, savedRequest)
        }
    }

    private fun checkIfProjectLeadIsAllowedToUpdate(updateEmployee: Employee?, savedRequest: WorkloadRequest) {
        val caller = getCallingEmployeeUseCase.execute()

        if (savedRequest.initiator != caller) throw UpdateWorkloadNotAllowedException("Project Lead can only update his requests")
        if (changedEmployeeFromOtherSubdivision(updateEmployee, savedRequest)) {
            throw UpdateWorkloadNotAllowedException("Project Lead cannot change employee from other subdivision")
        }
    }

    private fun checkIfLinearLeadIsAllowedToUpdate(updateEmployee: Employee?, savedRequest: WorkloadRequest) {
        if (changedEmployeeFromOtherSubdivision(updateEmployee, savedRequest)) {
            throw UpdateWorkloadNotAllowedException("Linear lead cannot change employee from other subdivision")
        }
    }

    private fun changedEmployeeFromOtherSubdivision(updateEmployee: Employee?,
                                                    savedRequest: WorkloadRequest): Boolean {

        updateEmployee ?: return false
        val changedOtherEmployee =
                checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(updateEmployee).not()


        val savedEmployee = savedRequest.employee?.id?.let(getEmployeeByIdUseCase::execute)
        val deletedOtherEmployee =
                savedEmployee?.let { checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(it).not() } ?: false

        return changedOtherEmployee or deletedOtherEmployee
    }
}