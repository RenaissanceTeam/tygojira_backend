package ru.fors.production.calendar.api.domain.entity

import java.time.LocalDate

class HolidayNotFoundException(date: LocalDate? = null): Throwable("No holiday found ${date?.let { "with date $it" }.orEmpty()}")