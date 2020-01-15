package ru.fors.employee.data.repo

import ru.fors.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepo : JpaRepository<Employee, Long>

