package ru.fors.production.calendar.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.GetHolidaysUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository

@Component
class GetHolidaysUseCaseImpl(
        private val holidaysRepository: HolidaysRepository
) : GetHolidaysUseCase {

    override fun execute(): List<Holiday> {
        return holidaysRepository.findAll()
    }
}