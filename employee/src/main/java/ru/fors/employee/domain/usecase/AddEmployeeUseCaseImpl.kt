package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.CheckCallerHasSystemRoleUseCase
import ru.fors.auth.api.domain.SignUpUseCase
import ru.fors.auth.api.domain.dto.Credentials
import ru.fors.auth.entity.SystemUserRole
import ru.fors.auth.entity.User
import ru.fors.employee.Employee
import ru.fors.employee.EmployeeRole
import ru.fors.employee.EmployeeUser
import ru.fors.employee.Role
import ru.fors.employee.api.domain.AddEmployeeUseCase
import ru.fors.employee.api.domain.dto.EmployeeDto
import ru.fors.employee.api.domain.dto.EmployeeWithRoleDto
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.employee.data.repo.EmployeeRoleRepo
import ru.fors.employee.data.repo.EmployeeUserRepo

@Component
class AddEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val roleRepo: EmployeeRoleRepo,
        private val checkCallerHasSystemRole: CheckCallerHasSystemRoleUseCase,
        private val signUpUseCase: SignUpUseCase,
        private val employeeUserRepo: EmployeeUserRepo
) : AddEmployeeUseCase {
    override fun execute(dto: EmployeeWithRoleDto): Employee {
        checkCallerHasSystemRole.execute(SystemUserRole.ADMIN)

        return saveEmployee(dto.employee).apply {
            saveEmployeeRole(this, dto.roles)
            val savedUser = signUpUser(this)
            saveUserToEmployeeConnection(this, savedUser)
        }
    }

    private fun saveUserToEmployeeConnection(savedEmployee: Employee, savedUser: User) {
        employeeUserRepo.save(EmployeeUser(
                employee = savedEmployee,
                user = savedUser
        ))
    }

    private fun signUpUser(savedEmployee: Employee): User {
        val newEmployeeCredentials = Credentials(
                savedEmployee.name,
                savedEmployee.name
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
                position = employee.position
        ))
    }
}