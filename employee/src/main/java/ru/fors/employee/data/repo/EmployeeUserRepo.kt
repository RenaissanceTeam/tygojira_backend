package ru.fors.employee.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeeUser

interface EmployeeUserRepo : JpaRepository<EmployeeUser, Long> {
    fun findByEmployee(employee: Employee): EmployeeUser?
    fun findByUserUsername(username: String): EmployeeUser?
}