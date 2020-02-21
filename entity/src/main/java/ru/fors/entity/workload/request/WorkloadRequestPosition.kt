package ru.fors.entity.workload.request

import java.util.*
import javax.persistence.*

@Entity
data class WorkloadRequestPosition(
        @Id @GeneratedValue
        val id: Long,
        val position: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        val skills: List<String>,
        val startDate: Date,
        val endDate: Date
)