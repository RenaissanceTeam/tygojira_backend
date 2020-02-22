package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee

interface GetCallingEmployeeUseCase {
    fun execute(): Employee
}