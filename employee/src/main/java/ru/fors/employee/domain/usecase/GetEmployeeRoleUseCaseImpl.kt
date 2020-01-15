package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.EmployeeRole
import ru.fors.employee.api.usecase.GetEmployeeRoleUseCase
import ru.fors.employee.data.repo.EmployeeRoleRepo

@Component
class GetEmployeeRoleUseCaseImpl(
        private val roleRepo: EmployeeRoleRepo
) : GetEmployeeRoleUseCase {
    override fun execute(username: String): EmployeeRole? {
        return roleRepo.findByEmployeeName(username)
    }
}