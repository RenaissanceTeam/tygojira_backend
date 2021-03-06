package ru.fors.employee.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.util.extensions.requireOne
import ru.fors.auth.api.domain.usecase.DeleteUserUseCase
import ru.fors.employee.api.domain.entity.EmployeeNotFoundException
import ru.fors.employee.api.domain.usecase.DeleteEmployeeUseCase
import ru.fors.employee.data.repo.EmployeeRepo
import ru.fors.employee.data.repo.EmployeeUserRepo
import ru.fors.entity.employee.Role

@Component
class DeleteEmployeeUseCaseImpl(
        private val employeeRepo: EmployeeRepo,
        private val employeeUserRepo: EmployeeUserRepo,
        private val deleteUserUseCase: DeleteUserUseCase,
        private val roleChecker: RoleChecker
) : DeleteEmployeeUseCase {
    override fun execute(id: Long) {
        roleChecker.requireOne(Role.LINEAR_LEAD)

        val employee = employeeRepo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id)
        val user = employeeUserRepo.findByEmployee(employee)?.user ?: throw Throwable("no associated user for employee $id")

        employeeRepo.delete(employee)
        deleteUserUseCase.execute(user)
    }
}