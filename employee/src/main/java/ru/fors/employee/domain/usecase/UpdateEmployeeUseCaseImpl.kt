package ru.fors.employee.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.usecase.UpdateEmployeeUseCase
import ru.fors.employee.api.domain.usecase.ValidateEmployeeUseCase
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.util.extensions.requireOne

@Component
class UpdateEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val roleChecker: RoleChecker,
        private val validateEmployeeUseCase: ValidateEmployeeUseCase
) : UpdateEmployeeUseCase {
    override fun execute(id: Long, updateInfo: UpdateEmployeeInfoDto): Employee {
        roleChecker.requireOne(Role.LINEAR_LEAD)

        val employee = employeeRepo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id)

        val updatedEmployee = employee.copy(
                firstName = updateInfo.firstName ?: employee.firstName,
                middleName = updateInfo.middleName ?: employee.middleName,
                lastName = updateInfo.lastName ?: employee.lastName,
                subdivision = updateInfo.subdivision ?: employee.subdivision,
                skills = updateInfo.skills ?: employee.skills,
                position = updateInfo.position ?: employee.position
        )

        validateEmployeeUseCase.execute(updatedEmployee)

        return employeeRepo.save(updatedEmployee)
    }
}