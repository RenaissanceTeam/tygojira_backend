package ru.fors.auth.api.domain

import ru.fors.entity.auth.User

interface GetCallingUserUseCase {
    fun execute(): User?
}