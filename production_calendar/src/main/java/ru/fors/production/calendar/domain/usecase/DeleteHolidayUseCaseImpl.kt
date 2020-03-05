package ru.fors.production.calendar.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.production.calendar.api.domain.usecase.CheckIfHolidaysModificationPermittedUseCase
import ru.fors.production.calendar.api.domain.usecase.DeleteHolidayUseCase
import ru.fors.production.calendar.api.domain.usecase.GetHolidayByIdUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository
import ru.fors.util.extensions.requireOne

@Component
class DeleteHolidayUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val holidaysRepository: HolidaysRepository,
        private val checkIfHolidaysModificationPermittedUseCase: CheckIfHolidaysModificationPermittedUseCase,
        private val getHolidayByIdUseCase: GetHolidayByIdUseCase
) : DeleteHolidayUseCase {

    override fun execute(id: Long) {
        roleChecker.requireOne(Role.PROJECT_OFFICE)

        val holiday = getHolidayByIdUseCase.execute(id)
        checkIfHolidaysModificationPermittedUseCase.execute(holiday)

        holidaysRepository.delete(holiday)
    }
}