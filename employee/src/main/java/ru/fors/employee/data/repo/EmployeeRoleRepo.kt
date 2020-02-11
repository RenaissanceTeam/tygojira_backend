package ru.fors.employee.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeRole

interface EmployeeRoleRepo : JpaRepository<EmployeeRole, Long> {
    fun findByEmployee(employee: Employee): EmployeeRole?
}