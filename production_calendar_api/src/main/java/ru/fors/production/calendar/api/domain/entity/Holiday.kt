package ru.fors.production.calendar.api.domain.entity

import java.time.LocalDate

data class Holiday(
        val title: String,
        val description: String,
        val date: LocalDate
)