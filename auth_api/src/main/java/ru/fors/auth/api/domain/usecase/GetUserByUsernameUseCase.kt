package ru.fors.auth.api.domain.usecase

import ru.fors.entity.auth.User

interface GetUserByUsernameUseCase {
    fun execute(username: String): User
}