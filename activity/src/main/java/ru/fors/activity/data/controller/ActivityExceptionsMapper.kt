package ru.fors.activity.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.activity.api.domain.entity.IllegalActivityClosureDateException
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class ActivityExceptionsMapper {

    @ExceptionHandler
    fun activityNotFound(e: ActivityNotFoundException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun illegalActivityClosureDate(e: IllegalActivityClosureDateException) = e.toResponseEntityStatus(HttpStatus.PRECONDITION_FAILED)
}