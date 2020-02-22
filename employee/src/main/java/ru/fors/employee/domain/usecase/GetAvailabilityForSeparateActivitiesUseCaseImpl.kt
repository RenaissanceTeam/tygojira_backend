package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetAvailabilityForSeparateActivitiesUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.employee.data.repo.SeparateActivityAvailabilityRepo
import ru.fors.entity.employee.SeparateActivityAvailability

@Component
class GetAvailabilityForSeparateActivitiesUseCaseImpl(
        private val repo: SeparateActivityAvailabilityRepo,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : GetAvailabilityForSeparateActivitiesUseCase {
    override fun execute(id: Long): SeparateActivityAvailability {
        return repo.findByEmployeeId(id) ?: saveEmptyAvailability(id)
    }

    private fun saveEmptyAvailability(id: Long): SeparateActivityAvailability {
        val employee = getEmployeeByIdUseCase.execute(id)
        return repo.save(SeparateActivityAvailability(employee = employee, dates = listOf()))
    }
}