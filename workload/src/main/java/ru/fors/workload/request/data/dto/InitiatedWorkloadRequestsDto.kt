package ru.fors.workload.request.data.dto

data class InitiatedWorkloadRequestsDto(
        val requests: List<InitiatedWorkloadRequestDto>
)

data class InitiatedWorkloadRequestDto(val id: Long, val status: String)