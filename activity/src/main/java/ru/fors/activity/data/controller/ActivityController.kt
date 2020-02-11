package ru.fors.activity.data.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.usecase.AddActivityUseCase
import ru.fors.activity.api.domain.usecase.GetActivitiesUseCase
import ru.fors.entity.activity.Activity
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.whenNotAllowedMapToResponseStatusException

@RestController
@RequestMapping("/activities")
class ActivityController(
        private val addActivityUseCase: AddActivityUseCase,
        private val getActivitiesUseCase: GetActivitiesUseCase
) {


    @PostMapping("/add")
    fun add(@RequestBody activityDto: ActivityDto): Activity {
        return addActivityUseCase.runCatching { execute(activityDto) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    @PostMapping("")
    fun getAll(@RequestBody pageRequest: PageRequest): Page<Activity> {
        return getActivitiesUseCase.execute(pageRequest)
    }

    private fun mapThrowable(throwable: Throwable) {
        when (val it = throwable.whenNotAllowedMapToResponseStatusException()) {
            else -> throw it
        }
    }
}