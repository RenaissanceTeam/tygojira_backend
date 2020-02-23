package ru.fors.production.calendar.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.DeleteHolidayUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository

@Component
class DeleteHolidayUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val holidaysRepository: HolidaysRepository
) : DeleteHolidayUseCase {

    override fun execute(holiday: Holiday) {
        roleChecker.startCheck()
                .require(Role.PROJECT_OFFICE)
                .requireAllSpecified()

        holidaysRepository.delete(holiday)
    }
}