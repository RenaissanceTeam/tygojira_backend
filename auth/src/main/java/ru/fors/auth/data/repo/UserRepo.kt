package ru.fors.auth.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.auth.User

interface UserRepo: JpaRepository<User, String> {
    fun findByUsername(username: String): User?
}


