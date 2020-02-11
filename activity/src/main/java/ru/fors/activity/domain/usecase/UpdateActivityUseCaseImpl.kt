package ru.fors.activity.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.activity.api.domain.usecase.UpdateActivityUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.requireOne
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Role

@Component
class UpdateActivityUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val repo: ActivityRepo
) : UpdateActivityUseCase {
    override fun execute(id: Long, activity: ActivityDto): Activity {
        roleChecker.requireOne(Role.LINEAR_LEAD)

        val savedActivity = repo.findByIdOrNull(id) ?: throw ActivityNotFoundException(id)

        return repo.save(savedActivity.copy(
                name = activity.name,
                startDate = activity.startDate,
                endDate = activity.endDate
        ))
    }
}