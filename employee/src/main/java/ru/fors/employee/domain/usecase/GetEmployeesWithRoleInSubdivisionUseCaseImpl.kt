package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetEmployeesWithRoleInSubdivisionUseCase
import ru.fors.employee.data.repo.EmployeeRoleRepo
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role

@Component
class GetEmployeesWithRoleInSubdivisionUseCaseImpl(
        private val employeeRoleRepo: EmployeeRoleRepo
        ) : GetEmployeesWithRoleInSubdivisionUseCase {
    override fun execute(role: Role, subdivision: String?): List<Employee> {
        return employeeRoleRepo.findByEmployeeSubdivisionAndRolesContaining(subdivision, role).map { it.employee }
    }
}