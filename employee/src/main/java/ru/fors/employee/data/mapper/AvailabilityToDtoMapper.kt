package ru.fors.employee.data.mapper

import org.springframework.stereotype.Component
import ru.fors.employee.data.dto.SeparateActivityAvailabilityDto
import ru.fors.entity.employee.SeparateActivityAvailability
import ru.fors.util.StringDateMapper

@Component
class AvailabilityToDtoMapper(
        private val dateMapper: StringDateMapper
) {
    fun map(availability: SeparateActivityAvailability): SeparateActivityAvailabilityDto {
        return SeparateActivityAvailabilityDto(
                id = availability.id,
                employeeId = availability.employee.id,
                dates = availability.dates.map(dateMapper::map)
        )
    }
}