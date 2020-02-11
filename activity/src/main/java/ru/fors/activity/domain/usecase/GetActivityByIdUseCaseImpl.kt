package ru.fors.activity.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.activity.api.domain.usecase.GetActivityByIdUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.entity.activity.Activity

@Component
class GetActivityByIdUseCaseImpl(
        private val repo: ActivityRepo
) : GetActivityByIdUseCase {
    override fun execute(id: Long): Activity {
        return repo.findByIdOrNull(id) ?: throw ActivityNotFoundException(id)
    }
}