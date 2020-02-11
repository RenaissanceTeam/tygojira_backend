package ru.fors.auth.api.domain.entity

class NoUserException(username: String): Throwable("There is no user: $username")