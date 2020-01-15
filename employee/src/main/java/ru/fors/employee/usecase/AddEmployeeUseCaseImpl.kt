package ru.fors.employee.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.Employee
import ru.fors.employee.EmployeeRole
import ru.fors.employee.api.usecase.AddEmployeeUseCase
import ru.fors.employee.api.usecase.dto.EmployeeWithRoleDto
import ru.fors.employee.repo.EmployeeRepo
import ru.fors.employee.repo.EmployeeRoleRepo

@Component
class AddEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val roleRepo: EmployeeRoleRepo
) : AddEmployeeUseCase {
    override fun execute(employee: EmployeeWithRoleDto) {
        val savedEmployee = employeeRepo.save(Employee(
                name = employee.employee.name,
                position = employee.employee.position
        ))

        roleRepo.save(EmployeeRole(
                employee = savedEmployee,
                roles = employee.roles.toSet()
        ))
    }
}