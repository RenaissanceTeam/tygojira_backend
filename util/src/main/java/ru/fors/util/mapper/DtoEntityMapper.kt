package ru.fors.util.mapper

interface DtoEntityMapper<ENTITY, DTO> {
    fun mapEntity(entity: ENTITY): DTO
    fun mapDto(dto: DTO): ENTITY
}