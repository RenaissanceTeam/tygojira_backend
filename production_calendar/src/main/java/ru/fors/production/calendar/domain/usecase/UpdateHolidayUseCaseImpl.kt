package ru.fors.production.calendar.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.entity.HolidayNotFoundException
import ru.fors.production.calendar.api.domain.usecase.UpdateHolidayUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository

@Component
class UpdateHolidayUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val holidaysRepository: HolidaysRepository
) : UpdateHolidayUseCase {

    override fun execute(holiday: Holiday) {
        roleChecker.startCheck()
                .require(Role.PROJECT_OFFICE)
                .requireAllSpecified()

        if (holidaysRepository.findByIdOrNull(holiday.date) == null) throw HolidayNotFoundException(holiday.date)

        holidaysRepository.save(holiday)
    }
}