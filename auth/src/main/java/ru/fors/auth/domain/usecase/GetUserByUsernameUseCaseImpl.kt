package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.entity.NoUserException
import ru.fors.auth.api.domain.usecase.GetUserByUsernameUseCase
import ru.fors.auth.data.repo.UserRepo
import ru.fors.entity.auth.User

@Component
class GetUserByUsernameUseCaseImpl(
        private val userRepo: UserRepo
) : GetUserByUsernameUseCase {
    override fun execute(username: String): User {
        return userRepo.findByUsername(username) ?: throw NoUserException(username)
    }
}