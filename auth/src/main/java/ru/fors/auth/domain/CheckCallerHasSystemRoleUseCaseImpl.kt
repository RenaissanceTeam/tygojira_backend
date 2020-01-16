package ru.fors.auth.domain

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.CheckCallerHasSystemRoleUseCase
import ru.fors.auth.api.domain.GetCallingUserUseCase
import ru.fors.auth.api.domain.GetSystemRoleByUsername
import ru.fors.auth.api.domain.entity.NoRoleInfoException
import ru.fors.auth.api.domain.entity.NoUserInfoException
import ru.fors.entity.auth.SystemUserRole

@Component
class CheckCallerHasSystemRoleUseCaseImpl(
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getSystemRoleByUsername: GetSystemRoleByUsername
) : CheckCallerHasSystemRoleUseCase {

    override fun execute(role: SystemUserRole): Boolean {
        val callingUser = getCallingUserUseCase.execute() ?: throw NoUserInfoException()
        val callingRole = getSystemRoleByUsername.execute(callingUser.username) ?: throw NoRoleInfoException()

        return callingRole.role == role
    }
}