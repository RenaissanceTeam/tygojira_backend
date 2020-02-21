package ru.fors.activity.domain.mapper

import org.springframework.stereotype.Component
import ru.fors.activity.api.domain.dto.ActivityDto
import ru.fors.entity.activity.Activity
import ru.fors.util.mapper.DtoEntityMapper
import ru.fors.util.mapper.StringDateMapper

@Component
class ActivityDtoEntityMapper(
        private val stringDateMapper: StringDateMapper
) : DtoEntityMapper<Activity, ActivityDto> {

    override fun mapEntity(entity: Activity) = ActivityDto(
            id = entity.id,
            startDate = stringDateMapper.map(entity.startDate),
            endDate = stringDateMapper.map(entity.endDate),
            name = entity.name
    )

    override fun mapDto(dto: ActivityDto) = Activity(
            id = dto.id ?: 0,
            name = dto.name,
            startDate = stringDateMapper.map(dto.startDate),
            endDate = stringDateMapper.map(dto.endDate)
    )

}