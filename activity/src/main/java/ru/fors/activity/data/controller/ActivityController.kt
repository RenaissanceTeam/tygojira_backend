package ru.fors.activity.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.activity.api.domain.usecase.*
import ru.fors.entity.activity.Activity
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.ExceptionMapper
import ru.fors.util.mapNotAllowed
import ru.fors.util.withExceptionMapper

@RestController
@RequestMapping("/activities")
class ActivityController(
        private val addActivityUseCase: AddActivityUseCase,
        private val getActivitiesUseCase: GetActivitiesUseCase,
        private val updateActivityUseCase: UpdateActivityUseCase,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val deleteActivityUseCase: DeleteActivityUseCase
) {


    @PostMapping("/add")
    fun add(@RequestBody activityDto: ActivityDto): Activity {
        return addActivityUseCase.runCatching { execute(activityDto) }
                .withExceptionMapper(::mapActivityControllerExceptions)
                .getOrThrow()
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Activity {
        return getActivityByIdUseCase.runCatching { execute(id) }
                .withExceptionMapper(::mapActivityControllerExceptions)
                .getOrThrow()
    }

    @PostMapping("")
    fun getAll(@RequestBody pageRequest: PageRequest): Page<Activity> {
        return getActivitiesUseCase.execute(pageRequest)
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody activity: ActivityDto): Activity {
        return updateActivityUseCase.runCatching { execute(id, activity) }
                .withExceptionMapper(::mapActivityControllerExceptions)
                .getOrThrow()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteActivityUseCase.runCatching { execute(id) }
                .withExceptionMapper(::mapActivityControllerExceptions)
    }

    private fun mapActivityControllerExceptions(mapper: ExceptionMapper) {
        mapper.notAllowed()
        mapper.responseStatus({ it is ActivityNotFoundException }, HttpStatus.NOT_FOUND)
    }
}
