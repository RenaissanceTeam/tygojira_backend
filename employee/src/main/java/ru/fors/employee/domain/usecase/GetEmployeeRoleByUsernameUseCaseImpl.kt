package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.entity.NoUserException
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.entity.employee.EmployeeRole
import ru.fors.employee.api.domain.usecase.GetEmployeeRoleByUsernameUseCase
import ru.fors.employee.data.repo.EmployeeRoleRepo
import ru.fors.employee.data.repo.EmployeeUserRepo

@Component
class GetEmployeeRoleByUsernameUseCaseImpl(
        private val roleRepo: EmployeeRoleRepo,
        private val employeeUserRepo: EmployeeUserRepo
) : GetEmployeeRoleByUsernameUseCase {
    override fun execute(username: String): EmployeeRole {
        val employeeUser = employeeUserRepo.findByUserUsername(username) ?: throw NoUserException(username)
        return roleRepo.findByEmployee(employeeUser.employee) ?: throw NoBusinessRoleException
    }
}