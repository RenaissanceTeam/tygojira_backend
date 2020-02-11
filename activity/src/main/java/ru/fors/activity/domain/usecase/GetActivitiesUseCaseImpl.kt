package ru.fors.activity.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.usecase.GetActivitiesUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.entity.activity.Activity
import ru.fors.pagination.api.domain.entity.Page
import ru.fors.pagination.api.domain.entity.PageRequest
import ru.fors.util.toPage
import ru.fors.util.toSpringPageRequest

@Component
class GetActivitiesUseCaseImpl (
        private val repo: ActivityRepo
): GetActivitiesUseCase {
    override fun execute(pageRequest: PageRequest): Page<Activity> {
        return repo.findAll(pageRequest.toSpringPageRequest()).toPage()
    }
}