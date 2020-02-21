package ru.fors.util.extensions

import ru.fors.pagination.api.domain.entity.Order
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import org.springframework.data.domain.Page as SpringPage
import org.springframework.data.domain.PageRequest as SpringPageRequest
import org.springframework.data.domain.Sort.Direction as SpringDirection

fun PageRequest.toSpringPageRequest(): SpringPageRequest = SpringPageRequest.of(
        page,
        size,
        sort.order.toSpringDirection(),
        *sort.fields.toTypedArray()
)

fun Order.toSpringDirection(): SpringDirection = when (this) {
    Order.ASCENDING -> SpringDirection.ASC
    else -> SpringDirection.DESC
}

fun <T> SpringPage<T>.toPage(): Page<T> = Page(
        content,
        totalElements,
        totalPages,
        number
)

fun <IN, OUT> Page<IN>.mapItems(mapper: (IN) -> OUT): Page<OUT> {
    return Page(
            items = items.map(mapper),
            currentPage = currentPage,
            totalItems = totalItems,
            totalPages = totalPages
    )
}