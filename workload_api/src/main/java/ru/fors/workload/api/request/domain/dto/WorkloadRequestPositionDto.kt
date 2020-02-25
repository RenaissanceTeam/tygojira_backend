package ru.fors.workload.api.request.domain.dto

import ru.fors.entity.workload.WorkUnit

data class WorkloadRequestPositionDto(
        val id: Long?,
        val position: String,
        val skills: List<String>,
        val workUnits: List<WorkUnit>,
        val employeeId: Long? = null
)