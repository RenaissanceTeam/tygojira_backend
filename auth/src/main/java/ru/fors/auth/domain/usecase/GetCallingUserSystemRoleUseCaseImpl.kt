package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.entity.NoSystemRoleInfoException
import ru.fors.auth.api.domain.entity.NoUserInfoException
import ru.fors.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.auth.api.domain.usecase.GetSystemRoleByUsernameUseCase
import ru.fors.entity.auth.SystemRole

@Component
class GetCallingUserSystemRoleUseCaseImpl(
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getSystemRoleByUsername: GetSystemRoleByUsernameUseCase
) : GetCallingUserSystemRoleUseCase {
    override fun execute(): SystemRole {
        val callingUser = getCallingUserUseCase.execute() ?: throw NoUserInfoException
        return getSystemRoleByUsername.execute(callingUser.username) ?: throw NoSystemRoleInfoException
    }
}