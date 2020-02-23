package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.CheckIfEmployeeIsFromCallerSubdivisionUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase

@Component
class CheckIfEmployeeIsFromCallerSubdivisionUseCaseImpl(
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : CheckIfEmployeeIsFromCallerSubdivisionUseCase {
    override fun execute(id: Long): Boolean {
        return getCallingEmployeeUseCase.execute().subdivision ==
                getEmployeeByIdUseCase.execute(id).subdivision
    }
}