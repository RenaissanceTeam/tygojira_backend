package ru.fors.auth.data.extensions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.entity.auth.SystemUserRole

fun CheckCallerHasSystemRoleUseCase.throwWhenNotAllowed(role: SystemUserRole) {
    if (execute(role)) return

    throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Caller must have $role permission")
}