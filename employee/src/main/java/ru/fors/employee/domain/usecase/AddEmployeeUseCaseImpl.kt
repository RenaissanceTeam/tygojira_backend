package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.usecase.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.entity.auth.SystemUserRole
import ru.fors.entity.auth.User
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole
import ru.fors.entity.employee.EmployeeUser
import ru.fors.entity.employee.Role
import ru.fors.employee.api.domain.usecase.AddEmployeeUseCase
import ru.fors.employee.api.domain.dto.EmployeeDto
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.employee.data.repo.EmployeeRoleRepo
import ru.fors.employee.data.repo.EmployeeUserRepo

@Component
class AddEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val roleRepo: EmployeeRoleRepo,
        private val roleChecker: RoleChecker,
        private val signUpUseCase: SignUpUseCase,
        private val employeeUserRepo: EmployeeUserRepo
) : AddEmployeeUseCase {
    override fun execute(dto: EmployeeWithRoleDto): Employee {
        roleChecker.startCheck()
                .require(SystemUserRole.ADMIN)
                .require(Role.LINEAR_LEAD)
                .requireAnySpecified()

        val savedUser = signUpUser(dto.employee.name, dto.employee.name)

        return saveEmployee(dto.employee).apply {
            saveEmployeeRole(this, dto.roles)
            saveUserToEmployeeConnection(this, savedUser)
        }
    }

    private fun saveUserToEmployeeConnection(savedEmployee: Employee, savedUser: User) {
        employeeUserRepo.save(EmployeeUser(
                employee = savedEmployee,
                user = savedUser
        ))
    }

    private fun signUpUser(name: String, password: String): User {
        val newEmployeeCredentials = Credentials(
                name,
                password
        )

        return signUpUseCase.execute(newEmployeeCredentials, SystemUserRole.USER)
    }

    private fun saveEmployeeRole(savedEmployee: Employee, roles: List<Role>) {
        roleRepo.save(EmployeeRole(
                employee = savedEmployee,
                roles = roles.toSet()
        ))
    }

    private fun saveEmployee(employee: EmployeeDto): Employee {
        return employeeRepo.save(Employee(
                name = employee.name,
                position = employee.position,
                subdivision = employee.subdivision
        ))
    }
}