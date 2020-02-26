package ru.fors.entity.workload

import ru.fors.entity.NOT_DEFINED_ID
import ru.fors.entity.employee.Employee
import javax.persistence.*

@Entity
data class Workload(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val workunits: List<WorkUnit>,
        @ManyToOne
        val employee: Employee
)