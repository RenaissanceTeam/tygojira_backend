package ru.fors.workload.request.data.repo

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadRequest

interface WorkloadRequestObservableRepo {
    fun observeAssigned(): SseEmitter
    fun observeInitiated(): SseEmitter
    fun onAssigned(employee: Employee, request: WorkloadRequest)
    fun onInitiatedChanged(employee: Employee, request: WorkloadRequest)
}