package ru.fors.employee.data.extensions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.entity.employee.Role

fun CheckUserHasBusinessRoleUseCase.throwWhenNotAllowed(role: Role) {
    if (execute(role)) return

    throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Caller must have $role permission")
}