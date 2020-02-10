package ru.fors.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.auth.api.domain.requireOne
import ru.fors.auth.api.domain.usecase.ChangePasswordUseCase
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.auth.api.domain.usecase.GetUserByUsernameUseCase
import ru.fors.auth.data.repo.UserRepo
import ru.fors.entity.auth.SystemUserRole

@Component
class ChangePasswordUseCaseImpl(
        private val roleChecker: RoleChecker,
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
        private val userRepo: UserRepo
) : ChangePasswordUseCase {
    override fun execute(username: String, password: String) {
        val callingUser = getCallingUserUseCase.execute()

        if (callingUser.username != username) {
            roleChecker.requireOne(SystemUserRole.ADMIN)

            val user = getUserByUsernameUseCase.execute(username)
            userRepo.save(user.copy(password = password))
        } else {
            userRepo.save(callingUser.copy(password = password))
        }
    }
}