package ru.fors.workload.api.request.domain.dto

import java.time.LocalDate

data class WorkloadRequestPositionDto(
        val id: Long?,
        val position: String,
        val skills: List<String>,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val employeeId: Long? = null
)