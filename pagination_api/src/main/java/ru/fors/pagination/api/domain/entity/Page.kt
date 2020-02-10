package ru.fors.pagination.api.domain.entity

data class Page<T>(val items: List<T>, val totalItems: Long, val totalPages: Int, val currentPage: Int)