package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.auth.SystemRole
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.auth.User
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.auth.api.domain.usecase.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.auth.data.SystemRoleRepo
import ru.fors.auth.data.UserRepo

@Component
open class SignUpUseCaseImpl(
        private val userRepo: UserRepo,
        private val systemRoleRepo: SystemRoleRepo,
        private val checkCallerRole: CheckCallerHasSystemRoleUseCase
) : SignUpUseCase {

    override fun execute(credentials: Credentials, role: SystemUserRole): User {
        checkThatHasProperSystemRolePermission(role)
        checkThatUsernameIsNotTaken(credentials.login)

        return userRepo.save(User(credentials.login, credentials.password)).also {
            systemRoleRepo.save(SystemRole(user = it, role = role))
        }
    }

    private fun checkThatUsernameIsNotTaken(login: String) {
        if (userRepo.findByUsername(login) != null) throw UserExistsException(login)
    }

    private fun checkThatHasProperSystemRolePermission(role: SystemUserRole) {
        if (role == SystemUserRole.SUPERUSER) throw NotAllowedException("can't create SUPERUSER")

        when (role) {
            SystemUserRole.ADMIN -> require(SystemUserRole.SUPERUSER)
            SystemUserRole.USER -> require(SystemUserRole.ADMIN)
            else -> {
            }
        }
    }

    private fun require(requiredRole: SystemUserRole) {
        if (!checkCallerRole.execute(requiredRole)) throw NotAllowedException("need ${requiredRole.name} rights")
    }
}