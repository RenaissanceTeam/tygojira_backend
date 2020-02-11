package ru.fors.auth.api.domain.usecase

interface ChangePasswordUseCase {
    fun execute(username: String, password: String)
}