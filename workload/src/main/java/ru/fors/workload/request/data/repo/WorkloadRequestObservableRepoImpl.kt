package ru.fors.workload.request.data.repo

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.usecase.GetAssignedWorkloadRequestByEmployeeUseCase
import ru.fors.workload.request.data.dto.AssignedWorkloadRequestsDto

@Component
class WorkloadRequestObservableRepoImpl(
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val getAssignedWorkloadRequestByEmployeeUseCase: GetAssignedWorkloadRequestByEmployeeUseCase
) : WorkloadRequestObservableRepo {
    private val emitters = mutableMapOf<Long, SseEmitter>()

    override fun observeAssigned(): SseEmitter {
        val employee = getCallingEmployeeUseCase.execute()

        return SseEmitter().apply {
            emitters[employee.id] = this
            onCompletion { emitters.remove(employee.id) }
            send(AssignedWorkloadRequestsDto(
                    getAssignedWorkloadRequestByEmployeeUseCase.execute(employee).map { it.id }
            ))
        }
    }

    override fun onAssigned(employee: Employee, request: WorkloadRequest) {
        emitters[employee.id]?.send(AssignedWorkloadRequestsDto(
                getAssignedWorkloadRequestByEmployeeUseCase.execute(employee).map { it.id }
        ))
    }
}