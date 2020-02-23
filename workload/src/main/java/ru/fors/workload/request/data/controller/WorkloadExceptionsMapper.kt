package ru.fors.workload.request.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.util.extensions.toResponseEntityStatus
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException

@ControllerAdvice
class WorkloadExceptionsMapper {

    @ExceptionHandler
    fun noWorkload(e: NoWorkloadFoundException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun updateNotAllowed(e: UpdateWorkloadNotAllowedException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)
}