package ru.fors.util

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.entity.NotAllowedException

fun RoleChecker.Setup.runOnFailureThrowSpringNotAllowed() {
    this.runCatching { runOnFailureThrow() }
            .onFailure {
                if (it is NotAllowedException) {
                    throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, it.message)
                }
            }

}