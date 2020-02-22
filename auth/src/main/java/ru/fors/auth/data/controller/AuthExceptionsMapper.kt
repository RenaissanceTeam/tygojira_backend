package ru.fors.auth.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.fors.auth.api.domain.entity.NoSystemRoleInfoException
import ru.fors.auth.api.domain.entity.NoUserException
import ru.fors.auth.api.domain.entity.NotAllowedException
import ru.fors.auth.api.domain.entity.UserExistsException
import ru.fors.util.extensions.toResponseEntityStatus

@ControllerAdvice
class AuthExceptionsMapper {

    @ExceptionHandler
    fun notAllowed(e: NotAllowedException) = e.toResponseEntityStatus(HttpStatus.METHOD_NOT_ALLOWED)

    @ExceptionHandler
    fun noSystemRoleInfo(e: NoSystemRoleInfoException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun noUser(e: NoUserException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler
    fun userExists(e: UserExistsException) = e.toResponseEntityStatus(HttpStatus.BAD_REQUEST)
}