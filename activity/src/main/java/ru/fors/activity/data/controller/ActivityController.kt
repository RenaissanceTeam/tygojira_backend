package ru.fors.activity.data.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.usecase.AddActivityUseCase
import ru.fors.entity.activity.Activity
import ru.fors.util.whenNotAllowedMapToResponseStatusException

@RestController
@RequestMapping("/activities")
open class ActivityController(
        private val addActivityUseCase: AddActivityUseCase
) {


    @PostMapping("/add")
    fun add(@RequestBody activityDto: ActivityDto): Activity {
        return addActivityUseCase.runCatching { execute(activityDto) }
                .onFailure(::mapThrowable)
                .getOrThrow()
    }

    private fun mapThrowable(throwable: Throwable) {
        when (val it = throwable.whenNotAllowedMapToResponseStatusException()) {
            else -> throw it
        }
    }
}