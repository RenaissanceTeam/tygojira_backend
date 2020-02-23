package ru.fors.workload.api.request.domain.entity

class NoWorkloadFoundException(id: Long? = null) : Throwable("No workload found ${id?.let { "for id $it" }.orEmpty()}")