package ru.fors.employee.data.repo

import ru.fors.entity.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepo : JpaRepository<Employee, Long>

