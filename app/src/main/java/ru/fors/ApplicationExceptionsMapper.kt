package ru.fors

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.util.dateFormat
import ru.fors.util.dateTimeFormat
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class ApplicationExceptionsMapper {


    @ExceptionHandler
    fun wrongDateFormat(e: InvalidFormatException) =
            e.toResponseEntityStatus(HttpStatus.BAD_REQUEST, message = "Date format should be $dateFormat or $dateTimeFormat")
}