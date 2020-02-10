package ru.fors.util

import ru.fors.pagination.api.domain.entity.Direction
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import org.springframework.data.domain.Page as SpringPage
import org.springframework.data.domain.PageRequest as SpringPageRequest
import org.springframework.data.domain.Sort.Direction as SpringDirection

fun PageRequest.toSpringPageRequest(): SpringPageRequest = SpringPageRequest.of(
        page,
        size,
        sort.direction.toSpringDirection(),
        *sort.fields.toTypedArray()
)

fun Direction.toSpringDirection(): SpringDirection = when (this) {
    Direction.ASC -> SpringDirection.ASC
    else          -> SpringDirection.DESC
}

fun <T> SpringPage<T>.toPage(): Page<T> = Page(
        content,
        totalElements,
        totalPages,
        number
)