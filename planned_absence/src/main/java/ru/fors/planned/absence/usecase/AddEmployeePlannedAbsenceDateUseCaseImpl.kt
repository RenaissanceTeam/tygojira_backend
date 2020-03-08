package ru.fors.planned.absence.usecase

import ru.fors.entity.employee.Employee
import ru.fors.planned.absence.api.usecase.AddEmployeePlannedAbsenceDateUseCase
import java.time.LocalDate

class AddEmployeePlannedAbsenceDateUseCaseImpl : AddEmployeePlannedAbsenceDateUseCase {

    override fun execute(employee: Employee, date: LocalDate) {
        TODO("Not yet implemented")
    }
}