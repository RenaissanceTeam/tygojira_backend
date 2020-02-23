package ru.fors.workload.request.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Role
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.dto.AddWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.WorkloadRequestDto
import ru.fors.workload.api.request.domain.usecase.AddWorkloadRequestUseCase
import ru.fors.workload.request.data.repo.WorkloadRequestRepo
import ru.fors.workload.request.domain.mapper.WorkloadRequestDtoToEntityMapper

@Component
class AddWorkloadRequestUseCaseImpl(
        private val repo: WorkloadRequestRepo,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val roleChecker: RoleChecker,
        private val workloadMapper: WorkloadRequestDtoToEntityMapper,
        private val checkIfEmployeeIsFromCallerSubdivisionUseCase: CheckIfEmployeeIsFromCallerSubdivisionUseCase

) : AddWorkloadRequestUseCase {

    override fun execute(requestDto: WorkloadRequestDto): WorkloadRequest {
        roleChecker.startCheck()
                .require(Role.PROJECT_LEAD)
                .require(Role.LINEAR_LEAD)
                .requireAnySpecified()

        val request = workloadMapper.mapDto(requestDto)
                .copy(initiator = getCallingEmployeeUseCase.execute())

        throwIfContainsEmployeeFromOtherSubdivision(request)

        return repo.save(request)
    }

    private fun throwIfContainsEmployeeFromOtherSubdivision(request: WorkloadRequest) {
        request.positions.filter {
            val employeeId = it.employeeId ?: return@filter false

            !checkIfEmployeeIsFromCallerSubdivisionUseCase.execute(employeeId)
        }.takeIf { it.isNotEmpty() }?.let {
            throw AddWorkloadNotAllowedException("Can't add employees from other subdivision")
        }
    }
}