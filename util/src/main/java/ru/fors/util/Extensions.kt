package ru.fors.util

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.entity.NotAllowedException

fun RoleChecker.Setup.requireAllOrThrowSpringNotAllowed() {
    this.runCatching { requireAllSpecified() }
            .onFailure(::whenNotAllowedMapToSpringException)
}


fun RoleChecker.Setup.requireAnyOrThrowSpringNotAllowed() {
    this.runCatching { requireAnySpecified() }
            .onFailure(::whenNotAllowedMapToSpringException)
}

private fun whenNotAllowedMapToSpringException(thr: Throwable) {
    if (thr is NotAllowedException) {
        throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, thr.message)
    }
}

