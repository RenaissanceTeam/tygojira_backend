package ru.fors.employee.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.employee.EmployeeRole

interface EmployeeRoleRepo : JpaRepository<EmployeeRole, Long> {
    fun findByEmployeeName(username: String): EmployeeRole?
}