package ru.fors.auth.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.dto.SystemUserRoleResponse
import ru.fors.auth.api.domain.dto.TokenResponse
import ru.fors.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.fors.auth.api.domain.usecase.SignInUseCase
import ru.fors.auth.api.domain.usecase.SignUpUseCase
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.employee.Role
import ru.fors.util.requireAnyOrThrowSpringNotAllowed
import ru.fors.util.requireOneOrThrowSpringNotAllowed

@RestController
open class AuthController(
        private val signInUseCase: SignInUseCase,
        private val signUpUseCase: SignUpUseCase,
        private val roleChecker: RoleChecker,
        private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase
) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: Credentials): TokenResponse {
        return signInUseCase.execute(credentials)
    }

    @PostMapping("/signup/admin")
    fun registerAdmin(@RequestBody credentials: Credentials) {
        roleChecker.requireOneOrThrowSpringNotAllowed(SystemUserRole.SUPERUSER)

        signUpUseCase.runCatching { execute(credentials, SystemUserRole.ADMIN) }
                .onFailure { throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message) }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody credentials: Credentials) {
        roleChecker.startCheck()
                .require(SystemUserRole.ADMIN)
                .require(Role.LINEAR_LEAD)
                .requireAnyOrThrowSpringNotAllowed()

        signUpUseCase.runCatching { execute(credentials, SystemUserRole.USER) }
                .onFailure { throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message) }
    }

    @GetMapping("/auth/role")
    fun getSystemUserRole(): SystemUserRoleResponse {
        return getCallingUserSystemRoleUseCase.runCatching { SystemUserRoleResponse(execute().role) }
                .getOrElse { throw ResponseStatusException(HttpStatus.BAD_REQUEST, it.message) }
    }
}