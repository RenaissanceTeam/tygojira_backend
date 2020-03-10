package ru.fors.monitoring.api.domain.dto

data class EmployeeActivitiesWorkloadsDto(
        val employeeId: Long,
        val activitiesWorkloads: List<ActivityWorkloadWithoutEmployeeDto>
)