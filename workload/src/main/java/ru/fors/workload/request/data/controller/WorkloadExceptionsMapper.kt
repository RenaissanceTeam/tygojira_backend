package ru.fors.workload.request.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.util.extensions.toResponseEntityStatus
import ru.fors.workload.api.domain.entity.NoWorkloadForActivityException
import ru.fors.workload.api.request.domain.dto.AddWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.dto.UpdateWorkloadNotAllowedException
import ru.fors.workload.api.request.domain.entity.NoActiveWorkloadPositionsException
import ru.fors.workload.api.request.domain.entity.NoEmployeeForPositionException
import ru.fors.workload.api.request.domain.entity.NoWorkloadFoundException
import ru.fors.workload.api.request.domain.entity.WorkloadLessThanMinimumException

@ControllerAdvice
class WorkloadExceptionsMapper {

    @ExceptionHandler
    fun noWorkload(e: NoWorkloadFoundException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun updateNotAllowed(e: UpdateWorkloadNotAllowedException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun addNotAllowed(e: AddWorkloadNotAllowedException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun hoursLessThanMinimum(e: WorkloadLessThanMinimumException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun noEmployeeAssigned(e: NoEmployeeForPositionException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun noActivePositions(e: NoActiveWorkloadPositionsException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler
    fun noWorkloadForActivity(e: NoWorkloadForActivityException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)
}