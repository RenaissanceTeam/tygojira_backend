package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeRoleByUsernameUseCase
import ru.fors.entity.employee.Role

@Component
class CheckUserHasBusinessRoleUseCaseImpl(
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getEmployeeRoleByUsernameUseCase: GetEmployeeRoleByUsernameUseCase
) : CheckUserHasBusinessRoleUseCase {
    override fun execute(role: Role): Boolean {
        val caller = getCallingUserUseCase.execute()
        val callerRole = getEmployeeRoleByUsernameUseCase.execute(caller.username)

        return callerRole.roles.contains(role)
    }
}