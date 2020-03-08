package ru.fors.planned.absence.api.usecase

import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeePlannedAbsence
import java.time.LocalDate
import java.time.Year

interface GetEmployeePlannedAbsenceDatesUseCase {
    fun execute(employee: Employee, year: Year = Year.now()): EmployeePlannedAbsence
}