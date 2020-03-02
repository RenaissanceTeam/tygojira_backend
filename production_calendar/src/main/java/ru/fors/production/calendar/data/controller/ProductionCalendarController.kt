package ru.fors.production.calendar.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.AddHolidayUseCase
import ru.fors.production.calendar.api.domain.usecase.DeleteHolidayUseCase
import ru.fors.production.calendar.api.domain.usecase.GetHolidaysUseCase
import ru.fors.production.calendar.api.domain.usecase.UpdateHolidayUseCase
import ru.fors.production.calendar.data.dto.HolidayDto
import ru.fors.production.calendar.data.mapper.HolidayDtoMapper
import java.time.Year

@RestController
@RequestMapping("/holidays")
class ProductionCalendarController(
        private val addHolidayUseCase: AddHolidayUseCase,
        private val deleteHolidayUseCase: DeleteHolidayUseCase,
        private val getHolidaysUseCase: GetHolidaysUseCase,
        private val updateHolidayUseCase: UpdateHolidayUseCase,
        private val holidayMapper: HolidayDtoMapper
) {

    @PostMapping
    fun add(@RequestBody holiday: HolidayDto): Holiday {
        return addHolidayUseCase.execute(holidayMapper.map(holiday))
    }

    @DeleteMapping
    fun delete(@RequestBody holiday: Holiday) {
        deleteHolidayUseCase.execute(holiday)
    }

    @GetMapping
    fun getHolidays(@RequestParam(required = false) year: Year?): List<Holiday> {
        val yearParam = year ?: Year.now()
        return getHolidaysUseCase.execute(yearParam)
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody holiday: HolidayDto): Holiday {
        return updateHolidayUseCase.execute(holidayMapper.map(holiday, id))
    }
}