package ru.fors.pagination.api.domain.entity

data class Sort(
        val order: Order,
        val fields: List<String>
)