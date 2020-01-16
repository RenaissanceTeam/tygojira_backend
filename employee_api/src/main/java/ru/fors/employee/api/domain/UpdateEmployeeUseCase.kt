package ru.fors.employee.api.domain

import ru.fors.entity.employee.Employee
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto

interface UpdateEmployeeUseCase {
    fun execute(id: Long, updateInfo: UpdateEmployeeInfoDto): Employee
}