package ru.fors.production.calendar.api.domain.entity

class HolidayModificationNotPermittedException: Throwable("Edit past holidays is not permitted")