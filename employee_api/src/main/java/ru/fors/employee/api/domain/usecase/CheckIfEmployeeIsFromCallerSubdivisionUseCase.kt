package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee

interface CheckIfEmployeeIsFromCallerSubdivisionUseCase {
    fun execute(employee: Employee): Boolean
}