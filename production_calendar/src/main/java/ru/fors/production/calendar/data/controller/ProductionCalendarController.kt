package ru.fors.production.calendar.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.entity.holiday.Holiday
import ru.fors.production.calendar.api.domain.usecase.AddHolidayUseCase
import ru.fors.production.calendar.api.domain.usecase.DeleteHolidayUseCase
import ru.fors.production.calendar.api.domain.usecase.GetHolidaysUseCase
import ru.fors.production.calendar.api.domain.usecase.UpdateHolidayUseCase

@RestController
@RequestMapping("/holidays")
class ProductionCalendarController(
        private val addHolidayUseCase: AddHolidayUseCase,
        private val deleteHolidayUseCase: DeleteHolidayUseCase,
        private val getHolidaysUseCase: GetHolidaysUseCase,
        private val updateHolidayUseCase: UpdateHolidayUseCase
) {

    @PostMapping
    fun add(@RequestBody holiday: Holiday) {
        addHolidayUseCase.execute(holiday)
    }

    @DeleteMapping
    fun delete(@RequestBody holiday: Holiday) {
        deleteHolidayUseCase.execute(holiday)
    }

    @GetMapping
    fun getHolidays(): List<Holiday> {
        return getHolidaysUseCase.execute()
    }

    @PatchMapping
    fun update(@RequestBody holiday: Holiday) {
        updateHolidayUseCase.execute(holiday)
    }
}