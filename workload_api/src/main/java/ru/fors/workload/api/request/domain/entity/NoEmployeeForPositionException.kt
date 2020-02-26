package ru.fors.workload.api.request.domain.entity

class NoEmployeeForPositionException(id: Long) : Throwable("No employee assigned for position $id")