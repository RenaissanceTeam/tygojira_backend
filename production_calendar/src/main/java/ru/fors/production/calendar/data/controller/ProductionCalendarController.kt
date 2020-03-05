package ru.fors.production.calendar.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.*
import ru.fors.production.calendar.data.dto.HolidayDto
import ru.fors.production.calendar.data.dto.HolidaysResponseDto
import ru.fors.production.calendar.data.mapper.HolidayDtoMapper
import java.time.Year

@RestController
@RequestMapping("/holidays")
class ProductionCalendarController(
        private val checkIfHolidaysModificationPermittedUseCase: CheckIfHolidaysModificationPermittedUseCase,
        private val addHolidayUseCase: AddHolidayUseCase,
        private val deleteHolidayUseCase: DeleteHolidayUseCase,
        private val getHolidaysUseCase: GetHolidaysUseCase,
        private val updateHolidayUseCase: UpdateHolidayUseCase,
        private val holidayMapper: HolidayDtoMapper
) {

    @PutMapping
    fun add(@RequestBody holidayDto: HolidayDto): Holiday {
        val holiday = holidayMapper.map(holidayDto)
        checkIfHolidaysModificationPermittedUseCase.execute(holiday)
        return addHolidayUseCase.execute(holiday)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteHolidayUseCase.execute(id)
    }

    @GetMapping
    fun getHolidays(@RequestParam(required = false) year: Year?): HolidaysResponseDto {
        val yearParam = year ?: Year.now()
        return HolidaysResponseDto(getHolidaysUseCase.execute(yearParam))
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody holidayDto: HolidayDto): Holiday {
        val holiday = holidayMapper.map(holidayDto, id)
        checkIfHolidaysModificationPermittedUseCase.execute(holiday)
        return updateHolidayUseCase.execute(holiday)
    }
}