package ru.fors.entity.workload.request

import ru.fors.entity.NOT_DEFINED_ID
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
data class WorkloadRequestPosition(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        val position: String,
        @ElementCollection(targetClass = String::class, fetch = FetchType.LAZY)
        val skills: List<String>,
        val startDate: LocalDate,
        val endDate: LocalDate
)