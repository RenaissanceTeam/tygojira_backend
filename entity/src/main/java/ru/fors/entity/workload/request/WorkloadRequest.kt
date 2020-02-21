package ru.fors.entity.workload.request

import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee
import javax.persistence.*

@Entity
data class WorkloadRequest(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        @ManyToOne
        val activity: Activity,
        @ManyToOne
        val initiator: Employee? = null,
        val status: String = IDLE,
        val targetRole: String? = null,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val positions: List<WorkloadRequestPosition>
)
