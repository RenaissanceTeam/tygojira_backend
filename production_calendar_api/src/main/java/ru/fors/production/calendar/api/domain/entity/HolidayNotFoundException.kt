package ru.fors.production.calendar.api.domain.entity

class HolidayNotFoundException(id: Long? = null): Throwable("No holiday found ${id?.let { "with id $it" }.orEmpty()}")