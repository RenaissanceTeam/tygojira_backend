package ru.fors

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class ApplicationExceptionsMapper(
        @Value("\${app.dateFormat}")
        private val dateFormat: String,
        @Value("\${app.dateTimeFormat}")
        private val dateTimeFormat: String
) {
    @ExceptionHandler
    fun wrongDateFormat(e: InvalidFormatException) = e.toResponseEntityStatus(
            status = HttpStatus.BAD_REQUEST,
            message = "Date format should be $dateFormat or $dateTimeFormat"
    )

    @ExceptionHandler
    fun illegalArgument(e: IllegalArgumentException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)
}