package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetPositionsUseCase
import ru.fors.employee.domain.gate.PositionsProvider

@Component
class GetPositionsUseCaseImpl(
        private val positionsProvider: PositionsProvider
) : GetPositionsUseCase {
    override fun execute(): List<String> {
        return positionsProvider.positions
    }
}