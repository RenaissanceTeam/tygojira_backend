package ru.fors.employee.api.domain.usecase

interface GetPositionsUseCase {
    fun execute(): List<String>
}