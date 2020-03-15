package ru.fors.workload.request.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.request.WorkloadNotification
import ru.fors.entity.workload.request.WorkloadNotificationType

interface WorkloadNotificationRepo : JpaRepository<WorkloadNotification, Long> {
    fun findByEmployeeAndType(employee: Employee, type: WorkloadNotificationType): List<WorkloadNotification>
    fun deleteByEmployeeAndType(employee: Employee, type: WorkloadNotificationType): List<WorkloadNotification>
}