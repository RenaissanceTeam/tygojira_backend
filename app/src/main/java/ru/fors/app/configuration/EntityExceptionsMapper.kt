package ru.fors.app.configuration

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.http.HttpStatus
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.util.EntityExceptionMapper

@Configuration
open class EntityExceptionsMapper {

    @EventListener(ApplicationReadyEvent::class)
    open fun onStart() {
        EntityExceptionMapper.mapper = {
            mapNotAllowed()
            responseStatus({ it is ActivityNotFoundException }, HttpStatus.NOT_FOUND)
            responseStatus({ it is EmployeeNotFoundException }, HttpStatus.NOT_FOUND)
            responseStatus({ it is NoBusinessRoleException }, HttpStatus.NOT_FOUND)
        }
    }
}
