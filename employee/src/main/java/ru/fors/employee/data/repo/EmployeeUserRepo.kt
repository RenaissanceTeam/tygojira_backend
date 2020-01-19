package ru.fors.employee.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.employee.EmployeeUser

interface EmployeeUserRepo : JpaRepository<EmployeeUser, Long>