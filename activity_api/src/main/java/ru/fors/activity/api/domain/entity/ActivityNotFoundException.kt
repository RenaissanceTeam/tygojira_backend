package ru.fors.activity.api.domain.entity

class ActivityNotFoundException(id: Long? = null): Throwable("No activity found ${id?.let { "with id $it" }.orEmpty()}")