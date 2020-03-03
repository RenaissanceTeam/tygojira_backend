package ru.fors.production.calendar.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.production.calendar.api.domain.entity.HolidayModificationNotPermittedException
import ru.fors.production.calendar.api.domain.entity.HolidayNotFoundException
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class ProductionCalendarExceptionsMapper {

    @ExceptionHandler
    fun noHoliday(e: HolidayNotFoundException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun holidayModificationNotPermitted(e: HolidayModificationNotPermittedException) = e.toResponseEntityStatus(HttpStatus.FORBIDDEN)
}