package ru.fors.activity.api.domain.dto

data class ActivityDto(
        val id: Long? = null,
        val name: String,
        val startDate: String,
        val endDate: String
)