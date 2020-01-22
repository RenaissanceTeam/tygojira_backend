package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.entity.NoUserInfoException
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.employee.api.domain.usecase.CheckUserHasBusinessRoleUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeRoleUseCase
import ru.fors.entity.employee.Role

@Component
class CheckUserHasBusinessRoleUseCaseImpl(
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getEmployeeRoleUseCase: GetEmployeeRoleUseCase
) : CheckUserHasBusinessRoleUseCase {
    override fun execute(role: Role): Boolean {
        val caller = getCallingUserUseCase.execute() ?: throw NoUserInfoException
        val callerRole = getEmployeeRoleUseCase.execute(caller.username) ?: throw NoBusinessRoleException

        return callerRole.roles.contains(role)
    }
}