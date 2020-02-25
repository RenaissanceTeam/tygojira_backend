package ru.fors.employee.data.repo

import ru.fors.entity.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface EmployeeRepo : JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>

