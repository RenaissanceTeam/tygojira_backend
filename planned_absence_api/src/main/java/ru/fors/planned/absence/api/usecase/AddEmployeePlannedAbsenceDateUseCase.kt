package ru.fors.planned.absence.api.usecase

import ru.fors.entity.employee.Employee
import java.time.LocalDate

interface AddEmployeePlannedAbsenceDateUseCase {
    fun execute(employee: Employee, date: LocalDate)
}