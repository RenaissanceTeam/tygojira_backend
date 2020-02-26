package ru.fors.workload.api.request.domain.entity

class NoActiveWorkloadPositionsException(request: Long): Throwable("No active positions specified for request='$request'")