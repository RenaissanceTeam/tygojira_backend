package ru.fors.employee.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto
import ru.fors.employee.api.domain.mapper.EmployeeToFullEmployeeInfoDtoMapper
import ru.fors.entity.employee.Employee

@Component
class EmployeeToFullEmployeeInfoDtoMapperImpl : EmployeeToFullEmployeeInfoDtoMapper {
    override fun map(employee: Employee): FullEmployeeInfoDto {
        return FullEmployeeInfoDto(
                id = employee.id,
                firstName = employee.firstName,
                middleName = employee.middleName,
                lastName = employee.lastName,
                position = employee.position,
                subdivision = employee.subdivision,
                skills = employee.skills
        )
    }
}