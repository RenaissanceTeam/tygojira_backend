package ru.fors.employee.api.domain.usecase

interface GetSubdivisionsUseCase {
    fun execute(): List<String>
}