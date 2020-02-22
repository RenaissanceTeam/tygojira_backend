package ru.fors.workload.api.request.domain.dto

data class WorkloadRequestPositionDto(
        val id: Long?,
        val position: String,
        val skills: List<String>,
        val startDate: String,
        val endDate: String
)