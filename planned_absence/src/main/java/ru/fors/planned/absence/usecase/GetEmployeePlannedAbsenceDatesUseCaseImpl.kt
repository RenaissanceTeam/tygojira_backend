package ru.fors.planned.absence.usecase

import ru.fors.entity.employee.Employee
import ru.fors.planned.absence.api.usecase.GetEmployeePlannedAbsenceDatesUseCase
import java.time.LocalDate
import java.time.Year

class GetEmployeePlannedAbsenceDatesUseCaseImpl : GetEmployeePlannedAbsenceDatesUseCase {

    override fun execute(employee: Employee, year: Year): List<LocalDate> {
        TODO("Not yet implemented")
    }
}