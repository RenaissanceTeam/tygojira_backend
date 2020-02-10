package ru.fors.pagination.api.domain

import ru.fors.pagination.api.domain.entity.Page

fun <T, U> Page<T>.map(f: (T) -> U): Page<U> = Page(
        items.map(f),
        totalItems,
        totalPages,
        currentPage
)