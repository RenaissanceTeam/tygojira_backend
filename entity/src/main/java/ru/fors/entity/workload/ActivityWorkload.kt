package ru.fors.entity.workload

import ru.fors.entity.activity.Activity
import javax.persistence.*

@Entity
data class ActivityWorkload(
        @Id @GeneratedValue
        val id: Long,
        @OneToOne
        val activity: Activity,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val workloads: List<Workload>
)