package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.usecase.GetFullEmployeesInfoUseCase
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.pagination.api.domain.map
import ru.fors.util.toPage
import ru.fors.util.toSpringPageRequest

@Component
class GetFullEmployeesInfoUseCaseImpl(
        private val employeeRepo: EmployeeRepo
) : GetFullEmployeesInfoUseCase {
    override fun execute(pageRequest: PageRequest): Page<FullEmployeeInfoDto> {
        return employeeRepo.findAll(pageRequest.toSpringPageRequest()).toPage().map {
            FullEmployeeInfoDto(
                    id = it.id,
                    name = it.name,
                    position = it.position,
                    subdivision = it.subdivision,
                    skills = it.skills,
                    workRoles = it.workRoles
            )
        }
    }
}