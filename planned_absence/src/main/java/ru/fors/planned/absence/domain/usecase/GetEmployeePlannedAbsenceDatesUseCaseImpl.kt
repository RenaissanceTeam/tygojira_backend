package ru.fors.planned.absence.domain.usecase

import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.EmployeePlannedAbsence
import ru.fors.planned.absence.api.usecase.GetEmployeePlannedAbsenceDatesUseCase
import ru.fors.planned.absence.data.repo.PlannedAbsenceRepository
import java.time.LocalDate
import java.time.Year

class GetEmployeePlannedAbsenceDatesUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val plannedAbsenceRepository: PlannedAbsenceRepository
) : GetEmployeePlannedAbsenceDatesUseCase {

    override fun execute(employee: Employee, year: Year): EmployeePlannedAbsence {
        TODO("Not yet implemented")
    }
}