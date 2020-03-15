package ru.fors.entity.workload.request

import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Employee
import javax.persistence.*

@Entity
data class WorkloadNotification(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        @ManyToOne
        val employee: Employee,
        @Enumerated(EnumType.ORDINAL)
        val type: WorkloadNotificationType
)