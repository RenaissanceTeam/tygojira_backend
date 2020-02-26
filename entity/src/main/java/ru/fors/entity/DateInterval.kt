package ru.fors.entity

import java.time.LocalDate

data class DateInterval(
        val from: LocalDate,
        val to: LocalDate
)