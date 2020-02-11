package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.usecase.DeleteUserUseCase
import ru.fors.auth.data.repo.UserRepo
import ru.fors.entity.auth.User

@Component
class DeleteUserUseCaseImpl(
        private val userRepo: UserRepo
) : DeleteUserUseCase {
    override fun execute(user: User) {
        userRepo.delete(user)
    }
}