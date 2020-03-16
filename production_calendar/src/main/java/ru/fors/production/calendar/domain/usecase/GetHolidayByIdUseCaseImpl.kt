package ru.fors.production.calendar.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.entity.HolidayNotFoundException
import ru.fors.production.calendar.api.domain.usecase.GetHolidayByIdUseCase
import ru.fors.production.calendar.data.repo.HolidaysRepository

@Component
class GetHolidayByIdUseCaseImpl(
        private val holidaysRepository: HolidaysRepository
) : GetHolidayByIdUseCase {
    override fun execute(id: Long): Holiday {
        return holidaysRepository.findByIdOrNull(id) ?: throw HolidayNotFoundException(id)
    }
}