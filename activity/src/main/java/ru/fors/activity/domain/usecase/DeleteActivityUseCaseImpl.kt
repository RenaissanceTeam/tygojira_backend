package ru.fors.activity.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.activity.api.domain.usecase.DeleteActivityUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.util.extensions.requireOne
import ru.fors.entity.employee.Role

@Component
class DeleteActivityUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val repo: ActivityRepo
) : DeleteActivityUseCase {
    override fun execute(id: Long) {
        roleChecker.requireOne(Role.LINEAR_LEAD)

        val activity = repo.findByIdOrNull(id) ?: throw ActivityNotFoundException(id)

        repo.delete(activity)
    }
}