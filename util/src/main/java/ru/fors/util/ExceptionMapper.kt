package ru.fors.util

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.entity.NotAllowedException

class ExceptionMapper {
    private val mappers = mutableListOf<(Throwable) -> Unit>()

    fun notAllowed() {
        mappers.add {
            if (it is NotAllowedException) throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, it.message)
        }
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