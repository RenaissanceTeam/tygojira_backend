package ru.fors.entity.workload

import ru.fors.entity.DEFAULT_WORKING_HOURS_IN_DAY
import ru.fors.entity.NOT_DEFINED_ID
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class WorkUnit(
        @Id @GeneratedValue
        val id: Long = NOT_DEFINED_ID,
        val date: LocalDate,
        val hours: Int = DEFAULT_WORKING_HOURS_IN_DAY
)