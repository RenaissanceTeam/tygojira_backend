package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeFilter
import ru.fors.employee.api.domain.usecase.GetFullEmployeesInfoUseCase
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.employee.data.specifications.toSpecification
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.pagination.api.domain.map
import ru.fors.util.extensions.toPage
import ru.fors.util.extensions.toSpringPageRequest

@Component
class GetFullEmployeesInfoUseCaseImpl(
        private val employeeRepo: EmployeeRepo
) : GetFullEmployeesInfoUseCase {
    override fun execute(pageRequest: PageRequest, filter: EmployeeFilter?): Page<FullEmployeeInfoDto> {

        return employeeRepo.findAll(filter?.toSpecification(), pageRequest.toSpringPageRequest())

                .toPage().map {
                    FullEmployeeInfoDto(
                            id = it.id,
                            firstName = it.firstName,
                            middleName = it.middleName,
                            lastName = it.lastName,
                            position = it.position,
                            subdivision = it.subdivision,
                            skills = it.skills
                    )
                }
    }
}