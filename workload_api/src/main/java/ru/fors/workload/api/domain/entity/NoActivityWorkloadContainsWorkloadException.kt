package ru.fors.workload.api.domain.entity

class NoActivityWorkloadContainsWorkloadException(id: Long) : Throwable("No activity workload for workload $id")