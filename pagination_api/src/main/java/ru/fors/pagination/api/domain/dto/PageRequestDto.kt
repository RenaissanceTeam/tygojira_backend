package ru.fors.pagination.api.domain.dto

import ru.fors.pagination.api.domain.entity.Order

data class PageRequestDto(
        val page: Int,
        val size: Int,
        val order: Order,
        val fields: List<String>
)