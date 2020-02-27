package ru.fors.entity.holiday

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Holiday(
        @Id @GeneratedValue
        val id: Long,
        val title: String,
        val description: String?,
        val startDate: LocalDate,
        val endDate: LocalDate = startDate
)