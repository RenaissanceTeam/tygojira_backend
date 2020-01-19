package ru.fors.employee.api.domain.usecase

import ru.fors.employee.api.domain.dto.FullEmployeeInfoDto

interface GetFullEmployeesInfoUseCase {
    fun execute(): List<FullEmployeeInfoDto>
}