package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.util.extensions.requireOne
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.employee.api.domain.usecase.SetAvailableForSeparateActivitiesUseCase
import ru.fors.employee.data.repo.SeparateActivityAvailabilityRepo
import ru.fors.entity.employee.Role
import ru.fors.entity.employee.SeparateActivityAvailability
import java.time.LocalDate

@Component
class SetAvailableForSeparateActivitiesUseCaseImpl(
        private val repo: SeparateActivityAvailabilityRepo,
        private val getEmployeeUseCase: GetEmployeeByIdUseCase,
        private val roleChecker: RoleChecker
) : SetAvailableForSeparateActivitiesUseCase {

    override fun execute(id: Long, available: List<LocalDate>): SeparateActivityAvailability {
        roleChecker.requireOne(Role.PROJECT_LEAD)

        val availability = repo.findByEmployeeId(id) ?: createAvailability(id)
        return repo.save(availability.copy(dates = available))
    }

    private fun createAvailability(id: Long): SeparateActivityAvailability {
        val employee = getEmployeeUseCase.execute(id)
        return SeparateActivityAvailability(employee = employee, dates = listOf())
    }
}
