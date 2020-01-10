package ru.fors.auth.data

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.auth.entity.EmployeeRole

interface EmployeeRoleRepo : JpaRepository<EmployeeRole, String> {
    fun findByUserUsername(username: String): EmployeeRole?
}