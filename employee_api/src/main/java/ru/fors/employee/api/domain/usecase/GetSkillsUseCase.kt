package ru.fors.employee.api.domain.usecase

interface GetSkillsUseCase {
    fun execute(): List<String>
}