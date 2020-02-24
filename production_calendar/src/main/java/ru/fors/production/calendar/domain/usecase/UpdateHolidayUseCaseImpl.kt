package ru.fors.production.calendar.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.entity.employee.Role
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.entity.HolidayNotFoundException
import ru.fors.production.calendar.api.domain.usecase.UpdateHolidayUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository
import ru.fors.util.extensions.requireOne

@Component
class UpdateHolidayUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val holidaysRepository: HolidaysRepository
) : UpdateHolidayUseCase {

    override fun execute(holiday: Holiday): Holiday {
        roleChecker.requireOne(Role.PROJECT_OFFICE)

        if (holidaysRepository.findById(holiday.date).isEmpty) throw HolidayNotFoundException(holiday.date)

        return holidaysRepository.save(holiday)
    }
}