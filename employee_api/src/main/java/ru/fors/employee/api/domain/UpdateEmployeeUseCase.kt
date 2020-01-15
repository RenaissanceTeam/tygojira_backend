package ru.fors.employee.api.domain

import ru.fors.employee.Employee
import ru.fors.employee.api.domain.dto.UpdateEmployeeInfoDto

interface UpdateEmployeeUseCase {
    fun execute(id: Long, updateInfo: UpdateEmployeeInfoDto): Employee
}