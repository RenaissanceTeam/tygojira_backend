package ru.fors.auth.data.utils

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.util.whenNotAllowedMapToResponseStatusException

fun mapThrowable(throwable: Throwable) {
    when (val it = throwable.whenNotAllowedMapToResponseStatusException()) {
        else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)
    }
}