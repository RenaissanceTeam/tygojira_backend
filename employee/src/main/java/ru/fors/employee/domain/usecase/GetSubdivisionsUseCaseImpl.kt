package ru.fors.employee.domain.usecase

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetSubdivisionsUseCase
import ru.fors.employee.domain.gate.SubdivisionsProvider

@Component
class GetSubdivisionsUseCaseImpl(
        private val subdivisionsProvider: SubdivisionsProvider
) : GetSubdivisionsUseCase {
    override fun execute(): List<String> {
        return subdivisionsProvider.subdivisions
    }
}