package ru.fors.util

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.entity.NotAllowedException

class ExceptionMapper {
    private val mappers = mutableListOf<(Throwable) -> Unit>()

    fun mapNotAllowed() {
        responseStatus({ it is NotAllowedException }, HttpStatus.METHOD_NOT_ALLOWED)
    }

    fun mapper(mapper: (Throwable) -> Unit) {
        mappers.add(mapper)
    }

    fun responseStatus(predicate: (Throwable) -> Boolean, status: HttpStatus, message: String? = null) {
        mapper {
            if (predicate(it)) throw ResponseStatusException(status, message ?: it.message)
        }
    }

    fun runChecksOrRethrow(throwable: Throwable) {
        mappers.forEach { it.invoke(throwable) }
        throw throwable
    }
}