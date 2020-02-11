package ru.fors.activity.api.domain.usecase

interface DeleteActivityUseCase {
    fun execute(id: Long)
}