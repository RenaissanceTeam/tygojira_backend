package ru.fors.pagination.api.domain.entity

data class Sort(
        val order: Order = Order.ASCENDING,
        val fields: List<String>
)