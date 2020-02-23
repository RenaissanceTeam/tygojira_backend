package ru.fors.employee.api.domain.usecase

interface CheckIfEmployeeIsFromCallerSubdivisionUseCase {
    fun execute(id: Long): Boolean
}