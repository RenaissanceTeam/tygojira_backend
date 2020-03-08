package ru.fors.planned.absence.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Employee
import ru.fors.planned.absence.api.usecase.DeleteEmployeePlannedAbsenceDateUseCase
import ru.fors.planned.absence.data.repo.PlannedAbsenceRepository
import java.time.LocalDate

@Component
class DeleteEmployeePlannedAbsenceDateUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val plannedAbsenceRepository: PlannedAbsenceRepository
) : DeleteEmployeePlannedAbsenceDateUseCase {

    override fun execute(employee: Employee, date: LocalDate) {
        TODO("Not yet implemented")
    }
}