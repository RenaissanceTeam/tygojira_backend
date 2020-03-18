package ru.fors.employee.domain.usecase

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetLeadedActivitiesUseCase
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeFilter
import ru.fors.employee.api.domain.mapper.EmployeeToFullEmployeeInfoDtoMapper
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeBusinessRolesUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetFullEmployeesInfoUseCase
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.employee.data.specifications.toSpecification
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.pagination.api.domain.map
import ru.fors.util.extensions.toPage
import ru.fors.util.extensions.toSpringPageRequest

@Component
class GetFullEmployeesInfoUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val getLeadedActivitiesUseCase: GetLeadedActivitiesUseCase,
        private val getCallingEmployeeBusinessRolesUseCase: GetCallingEmployeeBusinessRolesUseCase,
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val employeeToFullEmployeeInfoDtoMapper: EmployeeToFullEmployeeInfoDtoMapper
) : GetFullEmployeesInfoUseCase {
    override fun execute(pageRequest: PageRequest, filter: EmployeeFilter?): Page<FullEmployeeInfoDto> {
        val specification = addRoleRestrictionsToFilter(filter ?: EmployeeFilter())

        return employeeRepo.findAll(specification, pageRequest.toSpringPageRequest())
                .toPage()
                .map(employeeToFullEmployeeInfoDtoMapper::map)
    }

    private fun addRoleRestrictionsToFilter(filter: EmployeeFilter): Specification<Employee> {
        val roles = getCallingEmployeeBusinessRolesUseCase.execute()
        if (roles.contains(Role.PROJECT_OFFICE)) return filter.toSpecification()
        if (roles.contains(Role.LINEAR_LEAD)) return filter.copy(subdivision = getCallingEmployeeUseCase.execute().subdivision).toSpecification()
        if (roles.contains(Role.PROJECT_LEAD)) {
            return filter.copy(
                    subdivision = getCallingEmployeeUseCase.execute().subdivision,
                    activities = getLeadedActivitiesUseCase.execute()
            ).toSpecification()
        }
        return filter.toSpecification()
    }
}