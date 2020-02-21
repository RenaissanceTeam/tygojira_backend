package ru.fors.employee.api.domain.usecase

import ru.fors.entity.employee.Employee

interface GetEmployeeByIdUseCase {
    fun execute(id: Long): Employee
}