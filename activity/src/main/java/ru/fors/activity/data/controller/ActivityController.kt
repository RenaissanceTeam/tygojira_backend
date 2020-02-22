package ru.fors.activity.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.usecase.*
import ru.fors.activity.domain.mapper.ActivityDtoEntityMapper
import ru.fors.entity.activity.Activity
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.extensions.mapItems
import ru.fors.util.extensions.withEntityExceptionsMapper

@RestController
@RequestMapping("/activities")
class ActivityController(
        private val addActivityUseCase: AddActivityUseCase,
        private val getActivitiesUseCase: GetActivitiesUseCase,
        private val updateActivityUseCase: UpdateActivityUseCase,
        private val getActivityByIdUseCase: GetActivityByIdUseCase,
        private val deleteActivityUseCase: DeleteActivityUseCase,
        private val activityDtoEntityMapper: ActivityDtoEntityMapper
) {


    @PostMapping("/add")
    fun add(@RequestBody activityDto: ActivityDto): Activity {
        return addActivityUseCase.runCatching { execute(activityDto) }
                .withEntityExceptionsMapper()
                .getOrThrow()
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ActivityDto {
        return getActivityByIdUseCase.runCatching { execute(id) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .let(activityDtoEntityMapper::mapEntity)
    }

    @PostMapping("")
    fun getAll(@RequestBody pageRequest: PageRequest): Page<ActivityDto> {
        return getActivitiesUseCase.runCatching { execute(pageRequest) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .mapItems(activityDtoEntityMapper::mapEntity)
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody activity: ActivityDto): ActivityDto {
        return updateActivityUseCase.runCatching { execute(id, activity) }
                .withEntityExceptionsMapper()
                .getOrThrow()
                .let(activityDtoEntityMapper::mapEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteActivityUseCase.runCatching { execute(id) }
                .withEntityExceptionsMapper()
    }
}
