package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.auth.SystemRole
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.auth.User
import ru.fors.auth.api.domain.usecase.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.auth.api.domain.requireOne
import ru.fors.auth.data.repo.SystemRoleRepo
import ru.fors.auth.data.repo.UserRepo
import ru.fors.entity.employee.Role

@Component
open class SignUpUseCaseImpl(
        private val userRepo: UserRepo,
        private val systemRoleRepo: SystemRoleRepo,
        private val roleChecker: RoleChecker
) : SignUpUseCase {

    override fun execute(credentials: Credentials, role: SystemUserRole): User {
        when (role) {
            SystemUserRole.SUPERUSER -> throw NotAllowedException("can't create $role")
            SystemUserRole.ADMIN -> roleChecker.requireOne(SystemUserRole.SUPERUSER)
            SystemUserRole.USER -> roleChecker.startCheck()
                    .require(SystemUserRole.ADMIN)
                    .require(Role.LINEAR_LEAD)
                    .requireAnySpecified()
        }
        checkThatUsernameIsNotTaken(credentials.login)

        return userRepo.save(User(credentials.login, credentials.password)).also {
            systemRoleRepo.save(SystemRole(user = it, role = role))
        }
    }

    private fun checkThatUsernameIsNotTaken(login: String) {
        if (userRepo.findByUsername(login) != null) throw UserExistsException(login)
    }
}