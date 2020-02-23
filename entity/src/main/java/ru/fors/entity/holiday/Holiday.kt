package ru.fors.entity.holiday

import java.time.LocalDate
import javax.persistence.Id

data class Holiday(
        val title: String,
        val description: String,
        @Id
        val date: LocalDate
)