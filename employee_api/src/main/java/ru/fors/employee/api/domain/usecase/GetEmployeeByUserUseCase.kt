package ru.fors.employee.api.domain.usecase

import ru.fors.entity.auth.User
import ru.fors.entity.employee.Employee

interface GetEmployeeByUserUseCase {
    fun execute(user: User): Employee
}