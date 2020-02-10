package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.usecase.GetSystemRoleByUsernameUseCase
import ru.fors.auth.data.repo.SystemRoleRepo
import ru.fors.entity.auth.SystemRole

@Component
open class GetSystemRoleByUsernameUseCaseImpl(
        private val systemRoleRepo: SystemRoleRepo
) : GetSystemRoleByUsernameUseCase {
    override fun execute(username: String): SystemRole? {
        return systemRoleRepo.findByUserUsername(username)
    }
}