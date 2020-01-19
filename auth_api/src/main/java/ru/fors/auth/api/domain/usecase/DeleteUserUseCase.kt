package ru.fors.auth.api.domain.usecase

import ru.fors.entity.auth.User

interface DeleteUserUseCase {
    fun execute(user: User)
}