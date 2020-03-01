package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.entity.EmployeeValidationException
import ru.fors.employee.api.domain.usecase.GetPositionsUseCase
import ru.fors.employee.api.domain.usecase.GetSkillsUseCase
import ru.fors.employee.api.domain.usecase.GetSubdivisionsUseCase
import ru.fors.employee.api.domain.usecase.ValidateEmployeeUseCase
import ru.fors.entity.employee.Employee

@Component
class ValidateEmployeeUseCaseImpl(
        private val getSkillsUseCase: GetSkillsUseCase,
        private val getPositionsUseCase: GetPositionsUseCase,
        private val getSubdivisionsUseCase: GetSubdivisionsUseCase
) : ValidateEmployeeUseCase {
    override fun execute(employee: Employee) {
        validateSubdivision(employee)
        validatePosition(employee)
        validateSkills(employee)
    }

    private fun validateSkills(employee: Employee) {
        val validSkills = getSkillsUseCase.execute()
        employee.skills.filterNot { it in validSkills }
                .forEach { throw EmployeeValidationException(it) }
    }

    private fun validatePosition(employee: Employee) {
        employee.position.takeUnless { it in getPositionsUseCase.execute() }
                ?.let { throw EmployeeValidationException(it) }
    }

    private fun validateSubdivision(employee: Employee) {
        employee.subdivision.takeUnless { it in getSubdivisionsUseCase.execute() }
                ?.let { throw EmployeeValidationException(it) }
    }
}