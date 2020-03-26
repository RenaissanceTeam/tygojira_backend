package ru.fors.activity.api.domain.dto

import java.time.LocalDate

data class ActivityDto(
        val id: Long? = null,
        val name: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val leadId: Long
)