package ru.fors.entity.workload.request

import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee
import javax.persistence.*

@Entity
data class WorkloadRequest(
        @Id @GeneratedValue
        val id: Long = 0,
        @ManyToOne
        val activity: Activity,
        @ManyToOne
        val initiator: Employee? = null,
        val status: String = IDLE,
        val targetRole: String? = null,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val positions: List<WorkloadRequestPosition>
)
