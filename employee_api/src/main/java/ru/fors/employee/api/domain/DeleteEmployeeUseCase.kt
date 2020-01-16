package ru.fors.employee.api.domain

interface DeleteEmployeeUseCase {
    fun execute(id: Long)
}