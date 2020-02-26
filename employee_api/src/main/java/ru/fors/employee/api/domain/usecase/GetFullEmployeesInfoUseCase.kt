package ru.fors.employee.api.domain.usecase

import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.entity.EmployeeFilter
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest

interface GetFullEmployeesInfoUseCase {
    fun execute(pageRequest: PageRequest, filter: EmployeeFilter? = null): Page<FullEmployeeInfoDto>
}