package ru.fors.planned.absence.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeePlannedAbsence

interface PlannedAbsenceRepository : JpaRepository<EmployeePlannedAbsence, Employee> {
}