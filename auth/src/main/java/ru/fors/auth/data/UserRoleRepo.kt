package ru.fors.auth.data

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.auth.domain.entity.User
import ru.fors.auth.domain.entity.UserRole

interface UserRoleRepo : JpaRepository<UserRole, String> {
    fun findByUsername(username: String): UserRole?
}