package ru.fors.auth.api.domain.entity

class UserExistsException(username: String): Throwable("User $username already exists")