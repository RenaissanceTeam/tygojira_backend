package ru.fors.employee.data.provider

import org.springframework.stereotype.Component
import ru.fors.employee.domain.gate.PositionsProvider

@Component
class PositionsProviderImpl : PositionsProvider {
    override val positions = listOf(
            "Младший разработчик",
            "Разработчик",
            "Старший разработчик",
            "Тестировщик" // todo add more
    )
}