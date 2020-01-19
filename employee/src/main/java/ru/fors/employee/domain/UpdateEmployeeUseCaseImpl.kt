package ru.fors.employee.domain

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.EmployeeNotFoundException
import ru.fors.employee.api.domain.UpdateEmployeeUseCase
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto
import ru.fors.employee.data.repo.EmployeeRepo

@Component
class UpdateEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo
) : UpdateEmployeeUseCase {
    override fun execute(id: Long, updateInfo: UpdateEmployeeInfoDto): Employee {
        val employee = employeeRepo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id)

        return employeeRepo.save(
                employee.copy(
                        skills = updateInfo.skills ?: employee.skills,
                        workRoles = updateInfo.workRoles ?: employee.workRoles
                )
        )
    }
}