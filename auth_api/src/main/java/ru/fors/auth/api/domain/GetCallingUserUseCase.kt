package ru.fors.auth.api.domain

import ru.fors.auth.entity.User

interface GetCallingUserUseCase {
    fun execute(): User?
}