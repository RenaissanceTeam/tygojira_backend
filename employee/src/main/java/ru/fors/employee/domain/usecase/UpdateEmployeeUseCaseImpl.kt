package ru.fors.employee.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.requireOne
import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.usecase.UpdateEmployeeUseCase
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.entity.employee.Role

@Component
class UpdateEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val roleChecker: RoleChecker
) : UpdateEmployeeUseCase {
    override fun execute(id: Long, updateInfo: UpdateEmployeeInfoDto): Employee {
        roleChecker.requireOne(Role.LINEAR_LEAD)

        val employee = employeeRepo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id)

        return employeeRepo.save(
                employee.copy(
                        skills = updateInfo.skills ?: employee.skills,
                        position = updateInfo.position ?: employee.position
                )
        )
    }
}