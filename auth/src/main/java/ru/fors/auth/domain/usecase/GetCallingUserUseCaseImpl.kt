package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.entity.NoUserException
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.auth.data.repo.UserRepo
import ru.fors.auth.data.security.SecurityRepository
import ru.fors.entity.auth.User

@Component
open class GetCallingUserUseCaseImpl(
        private val securityRepository: SecurityRepository,
        private val userRepo: UserRepo
) : GetCallingUserUseCase {
    override fun execute(): User {
        val username = securityRepository.getAuthenticatedUsername()
        return userRepo.findByUsername(username) ?: throw NoUserException(username)
    }
}