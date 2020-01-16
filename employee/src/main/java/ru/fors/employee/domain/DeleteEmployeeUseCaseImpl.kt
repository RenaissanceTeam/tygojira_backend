package ru.fors.employee.domain

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.DeleteUserUseCase
import ru.fors.employee.api.domain.DeleteEmployeeUseCase
import ru.fors.employee.api.domain.EmployeeNotFoundException
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.employee.data.repo.EmployeeUserRepo

@Component
class DeleteEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val employeeUserRepo: EmployeeUserRepo,
        private val deleteUserUseCase: DeleteUserUseCase
) : DeleteEmployeeUseCase {
    override fun execute(id: Long) {
        val employee = employeeRepo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id)
        val user = employeeUserRepo.findByEmployee(employee)?.user ?: throw Throwable("no associated user for employee $id")

        employeeRepo.delete(employee)
        deleteUserUseCase.execute(user)
    }
}