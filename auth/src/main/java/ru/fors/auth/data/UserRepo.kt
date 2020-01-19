package ru.fors.auth.data

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.auth.entity.User

interface UserRepo: JpaRepository<User, String> {
    fun findByUsername(username: String): User?
}


