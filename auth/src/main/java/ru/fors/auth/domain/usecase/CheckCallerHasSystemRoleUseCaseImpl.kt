package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.fors.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.fors.entity.auth.SystemUserRole

@Component
class CheckCallerHasSystemRoleUseCaseImpl(
        private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase
) : CheckCallerHasSystemRoleUseCase {

    override fun execute(role: SystemUserRole): Boolean {
        val callingRole = getCallingUserSystemRoleUseCase.execute()
        return callingRole.role == role
    }
}