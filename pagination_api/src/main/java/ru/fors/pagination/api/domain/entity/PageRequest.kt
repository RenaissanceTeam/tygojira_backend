package ru.fors.pagination.api.domain.entity

data class PageRequest(
        val page: Int,
        val size: Int,
        val sort: Sort
)