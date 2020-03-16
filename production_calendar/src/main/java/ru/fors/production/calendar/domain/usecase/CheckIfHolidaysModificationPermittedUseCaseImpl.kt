package ru.fors.production.calendar.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.entity.HolidayModificationNotPermittedException
import ru.fors.production.calendar.api.domain.usecase.CheckIfHolidaysModificationPermittedUseCase
import java.time.Year

@Component
class CheckIfHolidaysModificationPermittedUseCaseImpl : CheckIfHolidaysModificationPermittedUseCase {

    override fun execute(holiday: Holiday) {
        val currentYear = Year.now()
        with(holiday) {
            if (startDate.year < currentYear.value && endDate.year < currentYear.value)
                throw HolidayModificationNotPermittedException()
        }
    }
}