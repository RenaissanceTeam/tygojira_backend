package ru.fors.employee.api.domain.mapper

import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.entity.employee.Employee

interface EmployeeToFullEmployeeInfoDtoMapper {
    fun map(employee: Employee): FullEmployeeInfoDto
}