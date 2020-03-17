package ru.fors.workload.api.request.domain.dto

import java.time.LocalDate

data class WorkloadScheduleDto(
        val start: LocalDate,
        val end: LocalDate,
        val monday: Int = 0,
        val tuesday: Int = 0,
        val wednesday: Int = 0,
        val thursday: Int = 0,
        val friday: Int = 0
)