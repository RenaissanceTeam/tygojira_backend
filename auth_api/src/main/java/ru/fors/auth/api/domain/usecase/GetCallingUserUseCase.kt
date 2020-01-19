package ru.fors.auth.api.domain.usecase

import ru.fors.entity.auth.User

interface GetCallingUserUseCase {
    fun execute(): User?
}