package ru.fors.auth.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.dto.TokenResponse
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.auth.api.domain.usecase.SignInUseCase
import ru.fors.auth.api.domain.usecase.SignUpUseCase
import ru.fors.auth.data.extensions.throwWhenNotAllowed
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role

@RestController
open class AuthController(
        private val signInUseCase: SignInUseCase,
        private val signUpUseCase: SignUpUseCase,
        private val checkCallerHasSystemRoleUseCase: CheckCallerHasSystemRoleUseCase,
        private val checkUserHasBusinessRoleUseCase: CheckUserHasBusinessRoleUseCase
) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: Credentials): TokenResponse {
        return signInUseCase.execute(credentials)
    }

    @PostMapping("/signup/admin")
    fun registerAdmin(@RequestBody credentials: Credentials) {
        checkCallerHasSystemRoleUseCase.throwWhenNotAllowed(SystemUserRole.SUPERUSER)

        signUpUseCase.runCatching { execute(credentials, SystemUserRole.ADMIN) }.onFailure {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody credentials: Credentials) {
        val isAdmin by lazy { checkCallerHasSystemRoleUseCase.execute(SystemUserRole.ADMIN) }
        val isLinearLead by lazy { checkUserHasBusinessRoleUseCase.execute(Role.LINEAR_LEAD) }

        if (!(isAdmin || isLinearLead)) {
            throw ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Must be either an ADMIN or LINEAR_LEAD")
        }


        signUpUseCase.runCatching { execute(credentials, SystemUserRole.USER) }.onFailure {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)
        }
    }
}