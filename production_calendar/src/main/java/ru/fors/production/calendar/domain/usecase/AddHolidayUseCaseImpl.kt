package ru.fors.production.calendar.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.AddHolidayUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository

@Component
class AddHolidayUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val holidaysRepository: HolidaysRepository
) : AddHolidayUseCase {

    override fun execute(holiday: Holiday) {
        roleChecker.startCheck()
                .require(Role.PROJECT_OFFICE)
                .requireAllSpecified()

        holidaysRepository.save(holiday)
    }
}