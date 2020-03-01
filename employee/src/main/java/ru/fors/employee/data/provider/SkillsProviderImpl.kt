package ru.fors.employee.data.provider

import org.springframework.stereotype.Component
import ru.fors.employee.domain.gate.SkillsProvider

@Component
class SkillsProviderImpl : SkillsProvider {
    override val skills = listOf(
            "Java",
            "JavaScript",
            "Python",
            "Kotlin",
            "Android",
            "Swift",
            "iOS" //todo add more
    )
}