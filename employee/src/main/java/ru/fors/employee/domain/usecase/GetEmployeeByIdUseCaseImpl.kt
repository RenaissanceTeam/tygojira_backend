package ru.fors.employee.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.entity.employee.Employee

@Component
class GetEmployeeByIdUseCaseImpl(
        private val repo: EmployeeRepo
) : GetEmployeeByIdUseCase {
    override fun execute(id: Long): Employee {
        return repo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id)
    }
}