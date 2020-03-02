package ru.fors.workload.request.data.repo

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadRequest
import ru.fors.workload.api.request.domain.usecase.GetAssignedWorkloadRequestByEmployeeUseCase
import ru.fors.workload.api.request.domain.usecase.GetInitiatedWorkloadRequestsByEmployeeUseCase
import ru.fors.workload.request.data.dto.AssignedWorkloadRequestsDto
import ru.fors.workload.request.data.dto.InitiatedWorkloadRequestDto
import ru.fors.workload.request.data.dto.InitiatedWorkloadRequestsDto

@Component
class WorkloadRequestObservableRepoImpl(
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val getAssignedWorkloadRequestByEmployeeUseCase: GetAssignedWorkloadRequestByEmployeeUseCase,
        private val getInitiatedWorkloadRequestsByEmployeeUseCase: GetInitiatedWorkloadRequestsByEmployeeUseCase
) : WorkloadRequestObservableRepo {
    private val assignEmitters = mutableMapOf<Long, SseEmitter>()
    private val initiateEmitters = mutableMapOf<Long, SseEmitter>()

    override fun observeAssigned(): SseEmitter {
        val employee = getCallingEmployeeUseCase.execute()

        return SseEmitter().apply {
            assignEmitters[employee.id] = this
            onCompletion { assignEmitters.remove(employee.id) }
            sendAssignedRequests(employee)
        }
    }

    private fun SseEmitter.sendAssignedRequests(employee: Employee) {
        send(AssignedWorkloadRequestsDto(
                getAssignedWorkloadRequestByEmployeeUseCase.execute(employee).map { it.id }
        ))
    }

    override fun onAssigned(employee: Employee, request: WorkloadRequest) {
        assignEmitters[employee.id]?.sendAssignedRequests(employee)
    }


    override fun observeInitiated(): SseEmitter {
        val employee = getCallingEmployeeUseCase.execute()

        return SseEmitter().apply {
            initiateEmitters[employee.id] = this
            onCompletion { initiateEmitters.remove(employee.id) }
            sendInitiatedRequests(employee)
        }
    }

    private fun SseEmitter.sendInitiatedRequests(employee: Employee) {
        send(InitiatedWorkloadRequestsDto(getInitiatedWorkloadRequestsByEmployeeUseCase.execute(employee)
                .map { InitiatedWorkloadRequestDto(it.id, it.status.name) }
        ))
    }

    override fun onInitiatedChanged(employee: Employee, request: WorkloadRequest) {
        initiateEmitters[employee.id]?.sendInitiatedRequests(employee)
    }
}