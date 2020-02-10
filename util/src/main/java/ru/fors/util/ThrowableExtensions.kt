package ru.fors.util

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.entity.NotAllowedException

fun Throwable.whenNotAllowedMapToResponseStatusException(): Throwable = when (this) {
    is NotAllowedException -> ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, this.message)
    else -> this
}