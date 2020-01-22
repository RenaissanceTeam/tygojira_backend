package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.auth.api.domain.usecase.GetSystemRoleByUsernameUseCase
import ru.fors.auth.api.domain.entity.NoSystemRoleInfoException
import ru.fors.auth.api.domain.entity.NoUserInfoException
import ru.fors.entity.auth.SystemUserRole

@Component
class CheckCallerHasSystemRoleUseCaseImpl(
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getSystemRoleByUsername: GetSystemRoleByUsernameUseCase
) : CheckCallerHasSystemRoleUseCase {

    override fun execute(role: SystemUserRole): Boolean {
        val callingUser = getCallingUserUseCase.execute() ?: throw NoUserInfoException
        val callingRole = getSystemRoleByUsername.execute(callingUser.username) ?: throw NoSystemRoleInfoException

        return callingRole.role == role
    }
}