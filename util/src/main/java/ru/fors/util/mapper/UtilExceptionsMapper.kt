package ru.fors.util.mapper

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class UtilExceptionsMapper {

    @ExceptionHandler
    fun badDateFormat(e: BadDateFormat) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)
}