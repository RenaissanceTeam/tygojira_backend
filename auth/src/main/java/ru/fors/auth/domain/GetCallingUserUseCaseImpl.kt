package ru.fors.auth.domain

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.GetCallingUserUseCase
import ru.fors.auth.data.UserRepo
import ru.fors.auth.data.security.SecurityRepository
import ru.fors.auth.entity.User

@Component
open class GetCallingUserUseCaseImpl(
        private val securityRepository: SecurityRepository,
        private val userRepo: UserRepo
) : GetCallingUserUseCase {
    override fun execute(): User? {
        val username = securityRepository.getAuthenticatedUsername()
        return userRepo.findByUsername(username)
    }
}