package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.entity.NoEmployeeLinkedWithUserException
import ru.fors.employee.api.domain.usecase.GetEmployeeByUserUseCase
import ru.fors.employee.data.repo.EmployeeUserRepo
import ru.fors.entity.auth.User
import ru.fors.entity.employee.Employee

@Component
class GetEmployeeByUserUseCaseImpl(
        private val repo: EmployeeUserRepo
) : GetEmployeeByUserUseCase {
    override fun execute(user: User): Employee {
        return repo.findByUserUsername(user.username)?.employee
                ?: throw NoEmployeeLinkedWithUserException(user.username)
    }
}