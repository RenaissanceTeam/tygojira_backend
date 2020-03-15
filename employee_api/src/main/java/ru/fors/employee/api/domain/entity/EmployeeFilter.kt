package ru.fors.employee.api.domain.entity

import ru.fors.entity.DateInterval
import ru.fors.entity.activity.Activity

data class EmployeeFilter(
        val firstName: String? = null,
        val middleName: String? = null,
        val lastName: String? = null,
        val position: String? = null,
        val subdivision: String? = null,
        val skills: List<String> = listOf(),
        val availableOn: List<DateInterval> = listOf(),
        val activities: List<Activity>? = null
)