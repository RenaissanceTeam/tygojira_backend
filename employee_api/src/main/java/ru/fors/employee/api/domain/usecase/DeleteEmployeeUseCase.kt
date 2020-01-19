package ru.fors.employee.api.domain.usecase

interface DeleteEmployeeUseCase {
    fun execute(id: Long)
}