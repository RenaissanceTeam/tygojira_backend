package ru.fors.activity.data.controller

import org.springframework.web.bind.annotation.*
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.usecase.*
import ru.fors.entity.activity.Activity
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest

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
        return addActivityUseCase.execute(activityDto)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Activity {
        return getActivityByIdUseCase.execute(id)
    }

    @PostMapping("")
    fun getAll(@RequestBody pageRequest: PageRequest): Page<Activity> {
        return getActivitiesUseCase.execute(pageRequest)
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody activity: ActivityDto): Activity {
        return updateActivityUseCase.execute(id, activity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        deleteActivityUseCase.execute(id)
    }
}
