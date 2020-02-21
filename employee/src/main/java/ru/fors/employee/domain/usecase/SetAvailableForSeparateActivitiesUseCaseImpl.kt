package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.employee.api.domain.usecase.SetAvailableForSeparateActivitiesUseCase
import ru.fors.employee.data.repo.SeparateActivityAvailabilityRepo
import ru.fors.entity.employee.SeparateActivityAvailability
import java.util.*

@Component
class SetAvailableForSeparateActivitiesUseCaseImpl(
        private val repo: SeparateActivityAvailabilityRepo,
        private val getEmployeeUseCase: GetEmployeeByIdUseCase
) : SetAvailableForSeparateActivitiesUseCase {

    override fun execute(id: Long, available: List<Date>): SeparateActivityAvailability {
        val availability = repo.findByEmployeeId(id) ?: createAvailability(id)
        return repo.save(availability.copy(dates = available))
    }

    private fun createAvailability(id: Long): SeparateActivityAvailability {
        val employee = getEmployeeUseCase.execute(id)
        return SeparateActivityAvailability(employee = employee, dates = listOf())
    }
}
