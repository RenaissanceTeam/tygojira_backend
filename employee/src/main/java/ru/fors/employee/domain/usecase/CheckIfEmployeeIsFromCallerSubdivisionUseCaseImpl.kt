package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.employee.Employee

@Component
class CheckIfEmployeeIsFromCallerSubdivisionUseCaseImpl(
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase
) : CheckIfEmployeeIsFromCallerSubdivisionUseCase {
    override fun execute(employee: Employee): Boolean {
        return getCallingEmployeeUseCase.execute().subdivision == employee.subdivision
    }
}