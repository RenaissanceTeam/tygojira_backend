package ru.fors.entity.workload.request

import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Employee
import ru.fors.entity.workload.WorkUnit
import javax.persistence.*

@Entity
data class WorkloadRequestPosition(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        val position: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        val skills: List<String>,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val workUnits: List<WorkUnit>,
        @ManyToOne(cascade = [CascadeType.ALL])
        val employee: Employee? = null,
        val active: Boolean = true
)