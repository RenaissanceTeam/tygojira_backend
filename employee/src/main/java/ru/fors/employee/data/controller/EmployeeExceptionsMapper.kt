package ru.fors.employee.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.employee.api.domain.entity.NoEmployeeLinkedWithUserException
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class EmployeeExceptionsMapper {

    @ExceptionHandler
    fun noEmployee(e: EmployeeNotFoundException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun noBusinessRole(e: NoBusinessRoleException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun noEmployeeLinkedWithUser(e: NoEmployeeLinkedWithUserException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)
}