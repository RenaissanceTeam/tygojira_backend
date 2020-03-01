package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee

interface ValidateEmployeeUseCase {
    fun execute(employee: Employee)
}