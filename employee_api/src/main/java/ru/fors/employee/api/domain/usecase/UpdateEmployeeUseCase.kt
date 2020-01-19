package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto

interface UpdateEmployeeUseCase {
    fun execute(id: Long, updateInfo: UpdateEmployeeInfoDto): Employee
}