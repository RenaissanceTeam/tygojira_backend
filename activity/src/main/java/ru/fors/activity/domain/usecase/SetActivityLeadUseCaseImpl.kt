package ru.fors.activity.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.entity.ActivityNotFoundException
import ru.fors.activity.api.domain.usecase.SetActivityLeadUseCase
import ru.fors.activity.data.repo.ActivityRepo
import ru.fors.auth.api.domain.RoleChecker
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.entity.activity.Activity
import ru.fors.entity.employee.Employee
import ru.fors.entity.employee.Role
import ru.fors.util.extensions.requireOne


@Component
class SetActivityLeadUseCaseImpl(
        private val repo: ActivityRepo,
        private val roleChecker: RoleChecker,
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : SetActivityLeadUseCase {
    override fun execute(id: Long, employeeId: Long): Activity {
        roleChecker.requireOne(Role.LINEAR_LEAD)
        val activity = repo.findByIdOrNull(id) ?: throw ActivityNotFoundException(id)

        return repo.save(activity.copy(lead = getEmployeeByIdUseCase.execute(employeeId)))
    }
}