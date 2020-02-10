package ru.fors.pagination.api.domain

import ru.fors.pagination.api.domain.dto.PageRequestDto
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.pagination.api.domain.entity.Sort

fun <T, U> Page<T>.map(f: (T) -> U): Page<U> = Page(
        items.map(f),
        totalItems,
        totalPages,
        currentPage
)

fun PageRequestDto.toPageRequest() = PageRequest(
        page,
        size,
        Sort(order, fields)
)