package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetSkillsUseCase
import ru.fors.employee.domain.gate.SkillsProvider

@Component
class GetSkillsUseCaseImpl(
        private val skillsProvider: SkillsProvider
) : GetSkillsUseCase {
    override fun execute(): List<String> {
        return skillsProvider.skills
    }
}