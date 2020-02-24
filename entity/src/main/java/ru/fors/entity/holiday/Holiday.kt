package ru.fors.entity.holiday

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Holiday(
        val title: String,
        val description: String,
        @Id
        val date: LocalDate
)