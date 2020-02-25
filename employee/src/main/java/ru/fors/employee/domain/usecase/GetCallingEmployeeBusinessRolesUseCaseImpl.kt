package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.entity.NoBusinessRoleException
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeBusinessRolesUseCase
import ru.fors.employee.api.domain.usecase.GetCallingEmployeeUseCase
import ru.fors.employee.data.repo.EmployeeRoleRepo
import ru.fors.entity.employee.Role

@Component
class GetCallingEmployeeBusinessRolesUseCaseImpl(
        private val getCallingEmployeeUseCase: GetCallingEmployeeUseCase,
        private val roleRepo: EmployeeRoleRepo
) : GetCallingEmployeeBusinessRolesUseCase {
    override fun execute(): Set<Role> {
        val employee = getCallingEmployeeUseCase.execute()
        return roleRepo.findByEmployee(employee)?.roles ?: throw NoBusinessRoleException(employee)
    }
}