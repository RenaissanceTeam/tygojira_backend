package ru.fors.employee.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole
import ru.fors.entity.employee.Role

interface EmployeeRoleRepo : JpaRepository<EmployeeRole, Long> {
    fun findByEmployee(employee: Employee): EmployeeRole?
    fun findByEmployeeSubdivisionAndRolesContaining(subdivision: String?, role: Role): List<EmployeeRole>
}