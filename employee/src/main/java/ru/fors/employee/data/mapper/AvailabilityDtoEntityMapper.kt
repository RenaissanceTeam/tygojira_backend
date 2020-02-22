package ru.fors.employee.data.mapper

import org.springframework.stereotype.Component
import ru.fors.employee.api.domain.usecase.GetEmployeeByIdUseCase
import ru.fors.employee.data.dto.SeparateActivityAvailabilityDto
import ru.fors.entity.employee.SeparateActivityAvailability
import ru.fors.util.mapper.DtoEntityMapper

@Component
class AvailabilityDtoEntityMapper(
        private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : DtoEntityMapper<SeparateActivityAvailability, SeparateActivityAvailabilityDto> {

    override fun mapEntity(entity: SeparateActivityAvailability): SeparateActivityAvailabilityDto {
        return SeparateActivityAvailabilityDto(
                entity.id,
                entity.employee.id,
                entity.dates
        )
    }

    override fun mapDto(dto: SeparateActivityAvailabilityDto): SeparateActivityAvailability {
        return SeparateActivityAvailability(
                dto.id,
                getEmployeeByIdUseCase.execute(dto.employeeId),
                dto.dates
        )
    }
}