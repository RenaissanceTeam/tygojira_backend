package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.employee.api.domain.usecase.GetBusinessRoleByEmployeeUseCase
import ru.fors.employee.data.repo.EmployeeRoleRepo
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role

@Component
class GetBusinessRoleByEmployeeUseCaseImpl(
        private val roleRepo: EmployeeRoleRepo
) : GetBusinessRoleByEmployeeUseCase {
    override fun execute(employee: Employee): Set<Role> {
        return roleRepo.findByEmployee(employee)?.roles ?: throw NoBusinessRoleException(employee)
    }
}