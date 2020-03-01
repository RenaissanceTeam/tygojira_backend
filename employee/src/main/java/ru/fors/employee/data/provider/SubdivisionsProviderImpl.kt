package ru.fors.employee.data.provider

import org.springframework.stereotype.Component
import ru.fors.employee.domain.gate.SubdivisionsProvider

@Component
class SubdivisionsProviderImpl : SubdivisionsProvider {
    override val subdivisions = listOf(
            "Форс центр разработки",
            "Форс Москва",
            "Форс Санкт-Петербург" // todo add more
    )
}