package ru.fors.planned.absence.usecase

import ru.fors.entity.employee.Employee
import ru.fors.planned.absence.api.usecase.DeleteEmployeePlannedAbsenceDateUseCase
import java.time.LocalDate

class DeleteEmployeePlannedAbsenceDateUseCaseImpl : DeleteEmployeePlannedAbsenceDateUseCase {

    override fun execute(employee: Employee, date: LocalDate) {
        TODO("Not yet implemented")
    }
}