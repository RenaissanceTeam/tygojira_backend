package ru.fors.auth.domain

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.GetCallingUserUseCase
import ru.fors.auth.api.domain.GetSystemRoleByUsername
import ru.fors.auth.api.domain.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.api.domain.entity.NoRoleInfoException
import ru.fors.auth.api.domain.entity.NoUserInfoException
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.auth.data.EmployeeRoleRepo
import ru.fors.auth.data.SystemRoleRepo
import ru.fors.auth.data.UserRepo
import ru.fors.auth.entity.*

@Component
open class SignUpUseCaseImpl(
        private val userRepo: UserRepo,
        private val systemRoleRepo: SystemRoleRepo,
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getSystemRoleByUsername: GetSystemRoleByUsername,
        private val employeeRoleRepo: EmployeeRoleRepo
) : SignUpUseCase {

    override fun execute(credentials: Credentials, role: SystemUserRoles) {
        checkThatHasProperSystemRolePermission(role)
        checkThatUsernameIsNotTaken(credentials.login)

        userRepo.save(User(credentials.login, credentials.password)).also {
            systemRoleRepo.save(SystemRole(user = it, role = role))
            employeeRoleRepo.save(EmployeeRole(user = it, roles = setOf(Roles.USER.name)))
        }
    }

    private fun checkThatUsernameIsNotTaken(login: String) {
        if (userRepo.findByUsername(login) != null) throw UserExistsException(login)
    }

    private fun checkThatHasProperSystemRolePermission(role: SystemUserRoles) {
        if (role == SystemUserRoles.SUPERUSER) throw NotAllowedException("can't create SUPERUSER")

        val callingUser = getCallingUserUseCase.execute() ?: throw NoUserInfoException()
        val callingRole = getSystemRoleByUsername.execute(callingUser.username) ?: throw NoRoleInfoException()

        when (role) {
            SystemUserRoles.ADMIN -> require(callingRole, SystemUserRoles.SUPERUSER)
            SystemUserRoles.USER -> require(callingRole, SystemUserRoles.ADMIN)
            else -> {
            }
        }
    }

    private fun require(callingRole: SystemRole, requiredRole: SystemUserRoles) {
        if (callingRole.role != requiredRole) throw NotAllowedException("need ${requiredRole.name} rights")
    }
}