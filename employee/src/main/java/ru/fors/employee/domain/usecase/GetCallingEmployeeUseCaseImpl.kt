package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.usecase.GetCallingUserUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByUserUseCase
import ru.fors.entity.employee.Employee

@Component
class GetCallingEmployeeUseCaseImpl(
        private val getCallingUserUseCase: GetCallingUserUseCase,
        private val getEmployeeByUserUseCase: GetEmployeeByUserUseCase
) : GetCallingEmployeeUseCase {
    override fun execute(): Employee {
        val user = getCallingUserUseCase.execute()
        return getEmployeeByUserUseCase.execute(user)
    }
}