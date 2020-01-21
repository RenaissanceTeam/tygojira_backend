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
        private val systemRoleRepo: SystemRoleRepo
) : SignUpUseCase {

    override fun execute(credentials: Credentials, role: SystemUserRole): User {
        if (role == SystemUserRole.SUPERUSER) throw NotAllowedException("can't create SUPERUSER")

        checkThatUsernameIsNotTaken(credentials.login)

        return userRepo.save(User(credentials.login, credentials.password)).also {
            systemRoleRepo.save(SystemRole(user = it, role = role))
        }
    }

    private fun checkThatUsernameIsNotTaken(login: String) {
        if (userRepo.findByUsername(login) != null) throw UserExistsException(login)
    }
}